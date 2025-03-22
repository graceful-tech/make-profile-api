package com.make_profile.service.impl.candidates;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.make_profile.dto.candidates.CandidateDto;
import com.make_profile.entity.candidates.CandidateAchievementsEntity;
import com.make_profile.entity.candidates.CandidateCertificateEntity;
import com.make_profile.entity.candidates.CandidateEntity;
import com.make_profile.entity.candidates.CandidateExperienceEntity;
import com.make_profile.entity.candidates.CandidateProjectEntity;
import com.make_profile.entity.candidates.CandidateQualificationEntity;
import com.make_profile.entity.templates.UsedTemplateEntity;
import com.make_profile.repository.candidates.CandidateAchievementsRepository;
import com.make_profile.repository.candidates.CandidateCertificateRepository;
import com.make_profile.repository.candidates.CandidateExperienceRepository;
import com.make_profile.repository.candidates.CandidateProjectRepository;
import com.make_profile.repository.candidates.CandidateQualificationRepository;
import com.make_profile.repository.candidates.CandidatesRepository;
import com.make_profile.repository.templates.TemplateAppliedRepository;
import com.make_profile.repository.templates.UsedTemplateRepository;
import com.make_profile.service.candidates.CandidateService;
import com.make_profile.service.candidates.TemplateService;

@Service
public class CandidatesServiceimpl implements CandidateService {

	private static final Logger logger = LoggerFactory.getLogger(CandidatesServiceimpl.class);

	@Autowired
	CandidatesRepository candidatesRepository;

	@Autowired
	CandidateExperienceRepository candidateExperienceRepository;

	@Autowired
	CandidateCertificateRepository candidateCertificateRepository;

	@Autowired
	CandidateProjectRepository candidateProjectRepository;

	@Autowired
	CandidateQualificationRepository candidateQualificationRepository;

	@Autowired
	CandidateAchievementsRepository candidateAchievementsRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	UsedTemplateRepository usedTemplateRepository;

	@Autowired
	TemplateAppliedRepository templateAppliedRepository;

	@Autowired
	TemplateService templateService;

	@Override
	public CandidateDto createCandidate(CandidateDto candidateDto) {

		logger.debug("Service :: createResumeTemplate :: Entered");

		CandidateDto candiateDto = null;

		try {

			if (Objects.nonNull(candidateDto.getName())) {
				candidateDto.setName(candidateDto.getName().replaceAll("\\s{2,}", " "));
			}

			CandidateEntity existingEntity = candidatesRepository
					.getCandidateDetailsByMobileNumber(candidateDto.getMobileNumber());

			if (Objects.nonNull(existingEntity)) {
				candidateDto.setId(existingEntity.getId());
			}

			CandidateEntity convertDtoToEntity = convertCandidateDtoToEntity(candidateDto);

			CandidateEntity candidateEntity = candidatesRepository.save(convertDtoToEntity);

			if (Objects.nonNull(candidateDto.getExperiences())) {
				convertCandidateExpeienceToEntity(candidateDto, candidateEntity);
			}

			if (Objects.nonNull(candidateDto.getQualification())) {
				convertCandidateQualificationToEntity(candidateDto, candidateEntity);

			}

			if (Objects.nonNull(candidateDto.getCertificates())) {
				convertCandidateCertificateToEntity(candidateDto, candidateEntity);
			}

			if (Objects.nonNull(candidateDto.getExperiences())) {
				convertCandidateAchievementsToEntity(candidateDto, candidateEntity);
			}

			UsedTemplateEntity usedTemplate = new UsedTemplateEntity();

			usedTemplate.setCandidateId(candidateEntity.getId());

			UsedTemplateEntity usedTemplateEntity = usedTemplateRepository.save(usedTemplate);

			templateService.saveCandidateDataInTemplate(usedTemplateEntity, candidateDto);

			usedTemplate = null;

//			candidateEntity = null;
			
			CandidateEntity candidate = candidatesRepository.findById(candidateEntity.getId()).get();

			candiateDto = modelMapper.map(candidatesRepository.findById(candidateEntity.getId()).get(),
					CandidateDto.class);
			
			candiateDto.setLanguagesKnown(Arrays.asList(candidate.getLanguagesKnown().split(",")));
			candiateDto.setSkills(Arrays.asList(candidate.getSkills().split(",")));
			

			candidateEntity = null;

		} catch (Exception e) {
			logger.debug("Service :: createResumeTemplate :: Exception" + e.getMessage());
		}
		logger.debug("Service :: createResumeTemplate :: Entered");
		return candiateDto;
	}

	public CandidateEntity convertCandidateDtoToEntity(CandidateDto candidateDto) {
		logger.debug("Service :: convertCandidateDtoToEntity :: Entered");
		CandidateEntity candidateEntity = null;
		try {
			candidateEntity = modelMapper.map(candidateDto, CandidateEntity.class);

			if (Objects.nonNull(candidateDto.getSkills())) {
				String skills = candidateDto.getSkills().stream().collect(Collectors.joining(","));
				candidateEntity.setSkills(skills);
			}

			if (Objects.nonNull(candidateDto.getLanguagesKnown())) {
				String languagesKnown = candidateDto.getLanguagesKnown().stream().collect(Collectors.joining(","));
				candidateEntity.setLanguagesKnown(languagesKnown);
			}

			if (Objects.isNull(candidateEntity.getIsFresher())) {
				candidateEntity.setIsFresher(false);
			}

		} catch (Exception e) {
			logger.error("Service :: convertCandidateDtoToEntity :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: convertCandidateDtoToEntity :: Exited");
		return candidateEntity;
	}

	public void convertCandidateExpeienceToEntity(CandidateDto candidateDto, CandidateEntity candidateEntity) {
		logger.debug("Service :: convertCandidateExpeienceToEntity :: Entered");

		try {
			if (Objects.nonNull(candidateDto.getExperiences()) && !candidateDto.getExperiences().isEmpty()) {

				candidateDto.getExperiences().stream().forEach(experience -> {

					if (Objects.isNull(experience.getId()) || experience.getId().equals("")) {

						CandidateExperienceEntity candidateExperience = new CandidateExperienceEntity();

						candidateExperience.setCandidateId(candidateEntity);
						candidateExperience.setCompanyName(experience.getCompanyName());
						candidateExperience.setRole(experience.getRole());
						candidateExperience.setCurrentlyWorking(experience.getCurrentlyWorking());
						candidateExperience.setExperienceYearStartDate(experience.getExperienceYearStartDate());
						candidateExperience.setExperienceYearEndDate(experience.getExperienceYearEndDate());

						CandidateExperienceEntity candidateExperienceId = candidateExperienceRepository
								.save(candidateExperience);

						candidateExperience = null;

						if (Objects.nonNull(experience.getProjects())) {

							experience.getProjects().stream().forEach(project -> {
								CandidateProjectEntity candidateProject = modelMapper.map(project,
										CandidateProjectEntity.class);

								candidateProject.setCandidateExperience(candidateExperienceId);
								candidateProjectRepository.save(candidateProject);
								candidateProject = null;
							});
						}
					} else {
						if (Objects.nonNull(experience.getId()) && experience.getId() > 0) {
							CandidateExperienceEntity experienceById = candidateExperienceRepository
									.getExperienceById(experience.getId());

							if (!experience.getIsDeleted()) {
								if (Objects.nonNull(experienceById)) {
									experienceById.setCandidateId(candidateEntity);
									experienceById.setCompanyName(experience.getCompanyName());
									experienceById.setRole(experience.getRole());
									experienceById.setCurrentlyWorking(experience.getCurrentlyWorking());
									experienceById.setExperienceYearStartDate(experience.getExperienceYearStartDate());
									experienceById.setExperienceYearEndDate(experience.getExperienceYearEndDate());

									CandidateExperienceEntity updateCandidateExperienceId = candidateExperienceRepository
											.save(experienceById);

									experienceById = null;

									if (Objects.nonNull(experience.getProjects())) {

										experience.getProjects().stream().forEach(project -> {

											if (!project.isProjectDeleted()) {

												if (Objects.nonNull(project.getId()) && !project.getId().equals("")) {
													CandidateProjectEntity projectById = candidateProjectRepository
															.getProjectById(project.getId());

													projectById.setProjectDescription(project.getProjectDescription());
													projectById.setProjectName(project.getProjectName());
													projectById.setProjectRole(project.getProjectRole());
													projectById.setCandidateExperience(updateCandidateExperienceId);
													if (Objects.nonNull(project.getProjectSkills())) {
														String projectSkills = project.getProjectSkills().stream()
																.collect(Collectors.joining(","));
														projectById.setProjectSkills(projectSkills);
													}

													candidateProjectRepository.save(projectById);
													projectById = null;
												} else {
													CandidateProjectEntity candidateProject = modelMapper.map(project,
															CandidateProjectEntity.class);

													candidateProject
															.setCandidateExperience(updateCandidateExperienceId);
													candidateProjectRepository.save(candidateProject);
													candidateProject = null;
												}
											} else {
												candidateProjectRepository.deleteById(project.getId());
											}
										});
									}
								}
							} else {
								candidateExperienceRepository.deleteById(experience.getId());

								templateAppliedRepository.deleteByCandidateSectionId(candidateEntity.getId(),
										"EXPERIENCE", experience.getId());
							}
						}
					}
				});
			}

		} catch (Exception e) {
			logger.error("Service :: convertCandidateExpeienceToEntity :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: convertCandidateExpeienceToEntity :: Exited");
	}

	public void convertCandidateQualificationToEntity(CandidateDto candidateDto, CandidateEntity candidateEntity) {
		logger.debug("Service :: convertCandidateQualificationToEntity :: Entered");
		try {

			if (Objects.nonNull(candidateDto.getQualification()) && !candidateDto.getQualification().isEmpty()) {

				candidateDto.getQualification().stream().forEach(qualification -> {

					if (Objects.isNull(qualification.getId()) || qualification.getId().equals("")) {

						CandidateQualificationEntity candidateQualification = modelMapper.map(qualification,
								CandidateQualificationEntity.class);

						candidateQualification.setCandidateId(candidateEntity);
						candidateQualificationRepository.save(candidateQualification);
					} else {
						if (Objects.nonNull(qualification.getId()) && qualification.getId() > 0) {
							CandidateQualificationEntity qualificationById = candidateQualificationRepository
									.getQualificationById(qualification.getId());

							if (!qualification.getIsDeleted()) {

								if (Objects.nonNull(qualificationById)) {

									qualificationById.setInstutionName(qualification.getInstutionName());
									qualificationById
											.setQualificationStartYear(qualification.getQualificationStartYear());
									qualificationById.setQualificationEndYear(qualification.getQualificationEndYear());
									qualificationById.setCandidateId(candidateEntity);
									candidateQualificationRepository.save(qualificationById);
								}
							} else {
								candidateQualificationRepository.deleteById(qualification.getId());
								templateAppliedRepository.deleteByCandidateSectionId(candidateEntity.getId(),
										"QUALIFICATION", qualification.getId());
							}
						}
					}
				});
			}

		} catch (Exception e) {
			logger.error("Service :: convertCandidateQualificationToEntity :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: convertCandidateQualificationToEntity :: Exited");
	}

	public void convertCandidateCertificateToEntity(CandidateDto candidateDto, CandidateEntity candidateEntity) {
		logger.debug("Service :: convertCandidateCertificateToEntity :: Entered");
		try {
			if (Objects.nonNull(candidateDto.getCertificates()) && !candidateDto.getCertificates().isEmpty()) {

				candidateDto.getCertificates().stream().forEach(certificate -> {

					if (Objects.isNull(certificate.getId()) || certificate.getId().equals("")) {
						CandidateCertificateEntity candidateCertificate = modelMapper.map(certificate,
								CandidateCertificateEntity.class);

						candidateCertificate.setCandidateId(candidateEntity);
						candidateCertificateRepository.save(candidateCertificate);
					} else {
						CandidateCertificateEntity certificateById = candidateCertificateRepository
								.getCertificateById(certificate.getId());

						if (!certificate.getIsDeleted()) {

							if (Objects.nonNull(certificateById)) {
								certificateById.setCourseName(certificate.getCourseName());
								certificateById.setCourseStartDate(certificate.getCourseStartDate());
								certificateById.setCourseEndDate(certificate.getCourseEndDate());
								certificateById.setCandidateId(candidateEntity);
								candidateCertificateRepository.save(certificateById);
							}
						} else {
							candidateCertificateRepository.deleteById(certificate.getId());
							templateAppliedRepository.deleteByCandidateSectionId(candidateEntity.getId(),
									"CERTIFICATES", certificate.getId());
						}
					}
				});
			}
		} catch (Exception e) {
			logger.error("Service :: convertCandidateCertificateToEntity :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: convertCandidateCertificateToEntity :: Exited");
	}

	public void convertCandidateAchievementsToEntity(CandidateDto candidateDto, CandidateEntity candidateEntity) {
		logger.debug("Service :: convertCandidateAchievementsToEntity :: Entered");
		try {
			if (Objects.nonNull(candidateDto.getAchievements()) && !candidateDto.getAchievements().isEmpty()) {

				candidateDto.getAchievements().stream().forEach(achievements -> {

					if (Objects.isNull(achievements.getId()) || achievements.getId().equals("")) {
						CandidateAchievementsEntity candidateAchievement = modelMapper.map(achievements,
								CandidateAchievementsEntity.class);

						candidateAchievement.setCandidateId(candidateEntity);
						candidateAchievementsRepository.save(candidateAchievement);

					} else {
						CandidateAchievementsEntity achievementsById = candidateAchievementsRepository
								.getAchievementsById(achievements.getId());

						if (!achievements.getIsDeleted()) {
							if (Objects.nonNull(achievementsById)) {
								achievementsById.setAchievementsName(achievements.getAchievementsName());
								achievementsById.setAchievementsDate(achievements.getAchievementsDate());
								achievementsById.setCandidateId(candidateEntity);

								candidateAchievementsRepository.save(achievementsById);
							}
						} else {
							candidateAchievementsRepository.deleteById(achievements.getId());
							templateAppliedRepository.deleteByCandidateSectionId(candidateEntity.getId(),
									"ACHIEVEMENTS", achievements.getId());
						}
					}
				});
			}
		} catch (Exception e) {
			logger.error("Service :: convertCandidateAchievementsToEntity :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: convertCandidateAchievementsToEntity :: Exited");
	}

}
