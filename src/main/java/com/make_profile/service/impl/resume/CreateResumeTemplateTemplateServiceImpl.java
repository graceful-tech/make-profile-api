package com.make_profile.service.impl.resume;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections4.map.HashedMap;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.make_profile.dto.candidates.CandidateAchievementsDto;
import com.make_profile.dto.candidates.CandidateCertificatesDto;
import com.make_profile.dto.candidates.CandidateDto;
import com.make_profile.dto.candidates.CandidateExperienceDto;
import com.make_profile.dto.candidates.CandidateProjectDetailsDto;
import com.make_profile.dto.candidates.CandidateQualificationDto;
import com.make_profile.service.openai.MakeProfileOpenAiService;
import com.make_profile.service.resume.CreateResumeTemplateService;
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

	@Override
	public void createResumeTemplate(CandidateDto candidateDto) {
		logger.debug("Service :: createResumeTemplate :: Extered");

		Map<String, Object> variables = new HashedMap<>();
		Template template = null;
		StringBuilder subject = new StringBuilder();
		try {

			variables.put("phone", candidateDto.getMobileNumber());
			variables.put("name", candidateDto.getName());
			variables.put("email", candidateDto.getEmail());
			// variables.put("summary", candidateDto.getSummary());

			if (Objects.nonNull(candidateDto.getLinkedIn()) && !candidateDto.getLinkedIn().isEmpty()) {
				variables.put("linkedin", candidateDto.getLinkedIn());
			}

			if (Objects.nonNull(candidateDto.getAddress()) && !candidateDto.getAddress().isEmpty()) {
				variables.put("address", candidateDto.getAddress());
			}

			if (Objects.nonNull(candidateDto.getDob())) {
				variables.put("dob", candidateDto.getDob());
			}

			if (Objects.nonNull(candidateDto.getGender()) && !candidateDto.getGender().isEmpty()) {
				variables.put("gender", candidateDto.getGender());
			}

			if (Objects.nonNull(candidateDto.getLanguagesKnown()) && !candidateDto.getLanguagesKnown().isEmpty()) {
				variables.put("languages", candidateDto.getLanguagesKnown());
			}

			if (Objects.nonNull(candidateDto.getMaritalStatus()) && !candidateDto.getMaritalStatus().isEmpty()) {
				variables.put("maritalStatus", candidateDto.getMaritalStatus());
			}

			if (candidateDto.getFresher()) {
				variables.put("isFresher", candidateDto.getFresher());
			} else {

				if (Objects.nonNull(candidateDto.getExperiences()) && !candidateDto.getExperiences().isEmpty()) {

					List<CandidateExperienceDto> experienceList = new ArrayList<>();
					List<CandidateProjectDetailsDto> projectsList = new ArrayList<>();

					candidateDto.getExperiences().forEach(exp -> {
						CandidateExperienceDto experiences = new CandidateExperienceDto();
						experiences.setRole(exp.getRole());
						experiences.setCompanyName(exp.getCompanyName());
						experiences.setExperienceYearStartDate(exp.getExperienceYearStartDate());
						experiences.setExperienceYearEndDate(exp.getExperienceYearEndDate());

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

			variables.put("skills", candidateDto.getSkills());

			if (Objects.nonNull(candidateDto.getCertificates()) && !candidateDto.getCertificates().isEmpty()) {

				List<CandidateCertificatesDto> certificatesList = new ArrayList<>();

				candidateDto.getCertificates().forEach(certificate -> {
					CandidateCertificatesDto cer = new CandidateCertificatesDto();

					cer.setCourseName(certificate.getCourseName());
					cer.setCourseStartDate(certificate.getCourseStartDate());
					cer.setCourseEndDate(certificate.getCourseEndDate());
					certificatesList.add(cer);

					cer = null;
				});

				variables.put("certifications", candidateDto.getCertificates());
			}

			if (Objects.nonNull(candidateDto.getQualification()) && !candidateDto.getQualification().isEmpty()) {
				List<CandidateQualificationDto> educationList = new ArrayList<>();

				candidateDto.getQualification().forEach(quali -> {
					CandidateQualificationDto qulification = new CandidateQualificationDto();

					qulification.setInstutionName(quali.getInstutionName());
					qulification.setDepartment(quali.getDepartment());
					qulification.setQualificationStartYear(quali.getQualificationStartYear());
					qulification.setQualificationEndYear(quali.getQualificationEndYear());
					qulification.setPercentage(quali.getPercentage());

					educationList.add(qulification);
					qulification = null;
				});
				variables.put("education", educationList);
			}

//            if (Objects.nonNull(candidateDto.getSoftSkills()) && !candidateDto.getSoftSkills().isEmpty()) {
//                variables.put("softSkills", candidateDto.getSoftSkills());
//            }

			if (Objects.nonNull(candidateDto.getAchievements()) && !candidateDto.getAchievements().isEmpty()) {

				List<CandidateAchievementsDto> achievementsList = new ArrayList<>();

				candidateDto.getAchievements().forEach(achieve -> {
					CandidateAchievementsDto achievements = new CandidateAchievementsDto();

					achievements.setAchievementsName(achieve.getAchievementsName());
					achievements.setAchievementsDate(achieve.getAchievementsDate());

					achievementsList.add(achievements);
					achievements = null;
				});
			}

			template = configuration.getTemplate("formal_resume.ftl");

			String processTemplateIntoString = FreeMarkerTemplateUtils.processTemplateIntoString(template, variables);

			makeProfileOpenAiService.makeProfileAi(processTemplateIntoString);

			convertHtmlToPdf(processTemplateIntoString, candidateDto.getName() + ".pdf");

			convertHtmlToDocx(processTemplateIntoString, candidateDto.getName() + ".docx");

		} catch (Exception e) {
			logger.debug("Service :: createResumeTemplate :: Exited" + e.getMessage());
		}
		logger.debug("Service :: createResumeTemplate :: Exited");
	}

	public static void convertHtmlToPdf(String html, String outputPath) throws Exception {

		logger.debug("Service :: convertHtmlToPdf :: Extered");
		try {
//			try (OutputStream os = new FileOutputStream(outputPath)) {
//				ITextRenderer renderer = new ITextRenderer();
//
//				SharedContext sharedContext = renderer.getSharedContext();
//				sharedContext.setPrint(true);
//				sharedContext.setInteractive(false);
//				sharedContext.getTextRenderer().setSmoothingThreshold(0);
//
//				// Set the HTML content
//				renderer.setDocumentFromString(html);
//				renderer.layout();
//				renderer.createPDF(os);
//				System.out.println("PDF generated successfully using Flying Saucer at: " + outputPath);
//				os.close();
//			}

			OutputStream os = new FileOutputStream(outputPath);
			PdfRendererBuilder builder = new PdfRendererBuilder();
			builder.useFastMode();
			builder.withHtmlContent(html, null);
			builder.toStream(os);
			builder.run();
			System.out.println("PDF generated successfully at: " + outputPath);

		}

		catch (Exception e) {
			logger.debug("Service :: convertHtmlToPdf :: Exited" + e.getMessage());
		}
		logger.debug("Service :: convertHtmlToPdf :: Exited");
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
			logger.debug("Service :: convertHtmlToDocx :: Exited" + e.getMessage());
		}
		logger.debug("Service :: convertHtmlToDocx :: Exited");
	}
}
