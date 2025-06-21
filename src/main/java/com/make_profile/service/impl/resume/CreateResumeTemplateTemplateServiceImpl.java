package com.make_profile.service.impl.resume;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.make_profile.dto.candidates.CandidateAchievementsDto;
import com.make_profile.dto.candidates.CandidateCertificatesDto;
import com.make_profile.dto.candidates.CandidateCollegeProjectDto;
import com.make_profile.dto.candidates.CandidateDto;
import com.make_profile.dto.candidates.CandidateExperienceDto;
import com.make_profile.dto.candidates.CandidateProjectDetailsDto;
import com.make_profile.dto.candidates.CandidateQualificationDto;
import com.make_profile.dto.master.ResponcePdfDto;
import com.make_profile.entity.master.CreditsEntity;
import com.make_profile.exception.MakeProfileException;
import com.make_profile.repository.candidates.CandidateImageRepository;
import com.make_profile.repository.master.CreditsRepository;
import com.make_profile.repository.user.UserRepository;
import com.make_profile.service.candidates.CheckResumePageCountService;
import com.make_profile.service.openai.MakeProfileOpenAiService;
import com.make_profile.service.resume.CreateResumeTemplateService;
import com.make_profile.utility.CommonConstants;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class CreateResumeTemplateTemplateServiceImpl implements CreateResumeTemplateService {

	private static final Logger logger = LoggerFactory.getLogger(CreateResumeTemplateTemplateServiceImpl.class);

	@Autowired
	private Configuration configuration;

	@Autowired
	MakeProfileOpenAiService makeProfileOpenAiService;

	@Autowired
	CheckResumePageCountService checkResumePageCountService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CreditsRepository creditsRepository;

	@Autowired
	CandidateImageRepository candidateImageRepository;

	@Override
	public ResponcePdfDto createResumeTemplate(CandidateDto candidate, String username) throws MakeProfileException {
		logger.debug("Service :: createResumeTemplate :: Extered");

		Map<String, Object> variables = new HashedMap<>();
		Template template = null;
		String imageLocation = null;
//		StringBuilder subject = new StringBuilder();

		byte[] convertHtmlToPdf = null;

		ResponcePdfDto responcePdfDto = new ResponcePdfDto();
		try {
//			CandidateDto candidateDto = convertCandidateDtoIntoString(candidate);

 			if (Objects.nonNull(candidate)) {

				variables.put("phone", candidate.getMobileNumber());
				variables.put("name", candidate.getName());
				variables.put("email", candidate.getEmail());
				// variables.put("summary", candidateDto.getSummary());

				if (Objects.nonNull(candidate.getLinkedIn()) && !candidate.getLinkedIn().isEmpty()) {
					variables.put("linkedin", candidate.getLinkedIn());
				}

				if (Objects.nonNull(candidate.getAddress()) && !candidate.getAddress().isEmpty()) {
					variables.put("address", candidate.getAddress());
				}

				if (Objects.nonNull(candidate.getDob())) {
					variables.put("dob", candidate.getDob());
				}

				if (Objects.nonNull(candidate.getGender()) && !candidate.getGender().isEmpty()) {
					variables.put("gender", candidate.getGender());
				}

				if (Objects.nonNull(candidate.getLanguagesKnown()) && !candidate.getLanguagesKnown().isEmpty()) {
					variables.put("languages", candidate.getLanguagesKnown());
				}

				if (Objects.nonNull(candidate.getMaritalStatus()) && !candidate.getMaritalStatus().isEmpty()) {
					variables.put("maritalStatus", candidate.getMaritalStatus());
				}

				if (candidate.isFresher()) {
					variables.put("isFresher", candidate.isFresher());
				} else {

					if (Objects.nonNull(candidate.getExperiences()) && !candidate.getExperiences().isEmpty()) {

						List<CandidateExperienceDto> experienceList = new ArrayList<>();
						List<CandidateProjectDetailsDto> projectsList = new ArrayList<>();

						candidate.getExperiences().forEach(exp -> {
							CandidateExperienceDto experiences = new CandidateExperienceDto();
							experiences.setRole(exp.getRole());
							experiences.setCompanyName(exp.getCompanyName());
							experiences.setExperienceYearStartDate(exp.getExperienceYearStartDate());
							experiences.setExperienceYearEndDate(exp.getExperienceYearEndDate());
							experiences.setResponsibilities(exp.getResponsibilities());

							if (Objects.nonNull(exp.getProjects()) && !exp.getProjects().isEmpty()) {
								exp.getProjects().forEach(project -> {
									CandidateProjectDetailsDto pro = new CandidateProjectDetailsDto();

									pro.setProjectRole(project.getProjectRole());
									pro.setProjectName(project.getProjectName());
									pro.setProjectDescription(project.getProjectDescription());
									pro.setProjectSkills(project.getProjectSkills());
									projectsList.add(pro);
									pro = null;
								});
							}
							experiences.setProjects(projectsList);
							experienceList.add(experiences);

							// projectsList.clear();
							experiences = null;
						});
						variables.put("experiences", experienceList);
					}
				}

				if (Objects.nonNull(candidate.getSkills()) && !candidate.getSkills().isEmpty()) {
					variables.put("skills", candidate.getSkills());
				}

				if (Objects.nonNull(candidate.getCertificates())
						&& !CollectionUtils.isEmpty(candidate.getCertificates())) {

					List<CandidateCertificatesDto> certificatesList = new ArrayList<>();

					candidate.getCertificates().forEach(certificate -> {
						CandidateCertificatesDto cer = new CandidateCertificatesDto();

						cer.setCourseName(certificate.getCourseName());
						cer.setCourseStartDate(certificate.getCourseStartDate());
						cer.setCourseEndDate(certificate.getCourseEndDate());
						certificatesList.add(cer);

						cer = null;
					});

					variables.put("certificates", candidate.getCertificates());
				}

				if (Objects.nonNull(candidate.getQualification())
						&& !CollectionUtils.isEmpty(candidate.getQualification())) {
					List<CandidateQualificationDto> educationList = new ArrayList<>();

					candidate.getQualification().forEach(quali -> {
						CandidateQualificationDto qulification = new CandidateQualificationDto();

						qulification.setInstitutionName(quali.getInstitutionName());
						qulification.setDepartment(quali.getDepartment());
						qulification.setQualificationStartYear(quali.getQualificationStartYear());
						qulification.setQualificationEndYear(quali.getQualificationEndYear());
						qulification.setPercentage(quali.getPercentage());

						educationList.add(qulification);
						qulification = null;
					});
					variables.put("education", educationList);
				}

				if (Objects.nonNull(candidate.getSoftSkills()) && !candidate.getSoftSkills().isEmpty()) {
					variables.put("softSkills", candidate.getSoftSkills());
				}

				if (Objects.nonNull(candidate.getAchievements())
						&& !CollectionUtils.isEmpty(candidate.getAchievements())) {

					List<CandidateAchievementsDto> achievementsList = new ArrayList<>();

					candidate.getAchievements().forEach(achieve -> {
						CandidateAchievementsDto achievements = new CandidateAchievementsDto();

						achievements.setAchievementsName(achieve.getAchievementsName());
						achievements.setAchievementsDate(achieve.getAchievementsDate());

						achievementsList.add(achievements);
						achievements = null;
					});
					variables.put("achievements", achievementsList);
				}

				if (Objects.nonNull(candidate.getCoreCompentencies())
						&& !candidate.getCoreCompentencies().isEmpty()) {
					variables.put("competencies", candidate.getCoreCompentencies());
				}

				if (Objects.nonNull(candidate.getCollegeProject())
						&& !CollectionUtils.isEmpty(candidate.getCollegeProject())) {

					List<CandidateCollegeProjectDto> candidateCollegeProjectList = new ArrayList<>();

					candidate.getCollegeProject().forEach(project -> {
						CandidateCollegeProjectDto collegeProject = new CandidateCollegeProjectDto();

						collegeProject.setCollegeProjectName(project.getCollegeProjectName());
						collegeProject.setCollegeProjectSkills(project.getCollegeProjectSkills());
						collegeProject.setCollegeProjectDescription(project.getCollegeProjectDescription());
						candidateCollegeProjectList.add(collegeProject);
						collegeProject = null;
					});
					variables.put("collegeProject", candidateCollegeProjectList);
				}

				if (Objects.nonNull(candidate.getCareerObjective())
						&& !candidate.getCareerObjective().isEmpty()) {
					variables.put("objective", candidate.getCareerObjective());
				}

				if (Objects.nonNull(candidate.getSummary()) && !candidate.getSummary().isEmpty()) {
					variables.put("summary", candidate.getSummary());
				}

				// add photo to the resume

				imageLocation = candidateImageRepository.getImageLocationByCandidateId(candidate.getId());

				if (Objects.nonNull(imageLocation) && !imageLocation.isEmpty()) {

					variables.put("profileImage", "data:image/png;base64,${base64Image}");
				}

				template = configuration.getTemplate(candidate.getTemplateName() + ".ftl");

				String processTemplateIntoString = FreeMarkerTemplateUtils.processTemplateIntoString(template,
						variables);

				// makeProfileOpenAiService.makeProfileAi(processTemplateIntoString);

				String resumeHtmlCode = checkResumePageCountService.getResumeHtmlCode(processTemplateIntoString,
						candidate.getId(), username, candidate.getTemplateName());

				if (Objects.nonNull(imageLocation) && !imageLocation.isEmpty()) {
					String imagePath = "C:/make_profile/Image/" + candidate.getId() + "/" + imageLocation;
					String base64Image = convertImageToBase64(imagePath);
					resumeHtmlCode = resumeHtmlCode.replace("${base64Image}", base64Image);

				}
				convertHtmlToPdf = convertHtmlToPdf(resumeHtmlCode, candidate.getName() + ".pdf");

				// convertHtmlToDocx(processTemplateIntoString, candidateDto.getName() +
				// ".docx");

				responcePdfDto.setResumePdf(convertHtmlToPdf);
				responcePdfDto.setCandidateName(candidate.getName());
 			}

			else {
				Long userId = userRepository.getUserId(username);
				CreditsEntity creditsEntity = creditsRepository.findCreditsByUserIdAndTemplateName(userId,
						candidate.getTemplateName());

				creditsEntity.setCreditAvailable(creditsEntity.getCreditAvailable() + 1);
				creditsEntity.setCreditUsed(creditsEntity.getCreditUsed() - 1);

				creditsRepository.save(creditsEntity);

				creditsEntity = null;
				userId = null;

				throw new MakeProfileException(CommonConstants.MP_0007);

			}

		} catch (MakeProfileException e) {
			logger.error("Service :: getResumeHtmlCode :: MakeProfileException :: " + e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("Service :: createResumeTemplate :: Exception " + e.getMessage());
		}
		logger.debug("Service :: createResumeTemplate :: Exited");
		return responcePdfDto;
	}

	public byte[] convertHtmlToPdf(String html, String outputPath) throws Exception {
		logger.debug("Service :: convertHtmlToPdf :: Entered");

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

			PdfRendererBuilder builder = new PdfRendererBuilder();
			builder.useDefaultPageSize(210, 297, PdfRendererBuilder.PageSizeUnits.MM);
			builder.withHtmlContent(html, new File(".").toURI().toString());
			builder.toStream(baos);
			builder.useFastMode();
			builder.run();

			byte[] pdfBytes = baos.toByteArray();

			builder = null;

			// TODO erase this after completing
			System.out.println("PDF generated successfully at: " + outputPath);

			logger.debug("Service :: convertHtmlToPdf :: Exited");
			return pdfBytes;

		} catch (Exception e) {
			logger.error("Service :: convertHtmlToPdf :: Error" + e.getMessage());
			return null;
		}
	}

	public static void convertHtmlToDocx(String html, String outputPath) throws Exception {
		logger.debug("Service :: convertHtmlToDocx :: Extered");
		try {

			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
			XHTMLImporterImpl xhtmlImporter = new XHTMLImporterImpl(wordMLPackage);
			wordMLPackage.getMainDocumentPart().getContent().addAll(xhtmlImporter.convert(html, null));
			wordMLPackage.save(new File(outputPath));

			System.out.println("Word file generated successfully: " + outputPath);
		} catch (Exception e) {
			logger.error("Service :: convertHtmlToDocx :: Exited" + e.getMessage());
		}
		logger.debug("Service :: convertHtmlToDocx :: Exited");
	}

	public CandidateDto convertCandidateDtoIntoString(CandidateDto candidateDto) {
		logger.debug("Service :: convertCandidateDtoIntoString :: Extered");

		CandidateDto ResponseCandidateDetailsFromOpenAi = null;
		try {
			StringBuilder dtoString = new StringBuilder("CandidateDto: {");

			dtoString.append("id=").append(candidateDto.getId()).append(", ");
			dtoString.append("name=").append(candidateDto.getName()).append(", ");
			dtoString.append("mobileNumber=").append(candidateDto.getMobileNumber()).append(", ");
			dtoString.append("alternateMobileNumber=").append(candidateDto.getAlternateMobileNumber()).append(", ");
			dtoString.append("email=").append(candidateDto.getEmail()).append(", ");
			dtoString.append("nationality=").append(candidateDto.getNationality()).append(", ");
			dtoString.append("gender=").append(candidateDto.getGender()).append(", ");
			dtoString.append("languagesKnown=").append(candidateDto.getLanguagesKnown()).append(", ");
			dtoString.append("isFresher=").append(candidateDto.isFresher()).append(", ");
			dtoString.append("skills=").append(candidateDto.getSkills()).append(", ");
			dtoString.append("linkedIn=").append(candidateDto.getLinkedIn()).append(", ");
			dtoString.append("dob=").append(candidateDto.getDob()).append(", ");
			dtoString.append("address=").append(candidateDto.getAddress()).append(", ");
			dtoString.append("maritalStatus=").append(candidateDto.getMaritalStatus()).append(", ");
			dtoString.append("experiences=").append(candidateDto.getExperiences()).append(", ");
			dtoString.append("qualification=").append(candidateDto.getQualification()).append(", ");
			dtoString.append("certificates=").append(candidateDto.getCertificates()).append(", ");
			dtoString.append("achievements=").append(candidateDto.getAchievements()).append(", ");
			dtoString.append("softSkills=").append(candidateDto.getSoftSkills()).append(", ");
			dtoString.append("coreCompentencies=").append(candidateDto.getCoreCompentencies()).append(", ");
			dtoString.append("collegeProject=").append(candidateDto.getCollegeProject());

			dtoString.append("}");

			ResponseCandidateDetailsFromOpenAi = makeProfileOpenAiService.getSummaryFromAi(dtoString.toString());

			dtoString = null;
		}

		catch (Exception e) {
			logger.error("Service :: convertCandidateDtoIntoString :: Exception" + e.getMessage());
		}
		logger.debug("Service :: convertCandidateDtoIntoString :: Exited");
		return ResponseCandidateDetailsFromOpenAi;

	}

	public String convertImageToBase64(String imagePath) {
		logger.debug("Service :: convertImageToBase64 :: Extered");

		Path path = Paths.get(imagePath);
		byte[] imageBytes = null;
		try {
			imageBytes = Files.readAllBytes(path);
		} catch (Exception e) {
			logger.error("Service :: convertImageToBase64 :: Exception" + e.getMessage());

		}
		logger.debug("Service :: convertImageToBase64 :: Exited");
		return Base64.getEncoder().encodeToString(imageBytes);
	}

	@Override
	public CandidateDto getContent(CandidateDto candidateDto, String username) throws MakeProfileException {
		logger.debug("Service :: getContent :: Extered");

		CandidateDto candidate = null;
		try {
			candidate = convertCandidateDtoIntoString(candidateDto);

		} catch (Exception e) {
			logger.error("Service :: getContent :: Exception" + e.getMessage());
		}
		logger.debug("Service :: getContent :: Exited");
		return candidate;
	}

}
