package com.make_profile.service.impl.candidates;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.make_profile.dto.candidates.CandidateAchievementsDto;
import com.make_profile.dto.candidates.CandidateCertificatesDto;
import com.make_profile.dto.candidates.CandidateDto;
import com.make_profile.dto.candidates.CandidateExperienceDto;
import com.make_profile.dto.candidates.CandidateImageDto;
import com.make_profile.dto.candidates.CandidateProjectDetailsDto;
import com.make_profile.dto.candidates.CandidateQualificationDto;
import com.make_profile.entity.candidates.CandidateAchievementsEntity;
import com.make_profile.entity.candidates.CandidateCertificateEntity;
import com.make_profile.entity.candidates.CandidateCollegeProjectEntity;
import com.make_profile.entity.candidates.CandidateEntity;
import com.make_profile.entity.candidates.CandidateExperienceEntity;
import com.make_profile.entity.candidates.CandidateImageEntity;
import com.make_profile.entity.candidates.CandidateProjectEntity;
import com.make_profile.entity.candidates.CandidateQualificationEntity;
import com.make_profile.entity.templates.UsedTemplateEntity;
import com.make_profile.repository.candidates.CandidateAchievementsRepository;
import com.make_profile.repository.candidates.CandidateCertificateRepository;
import com.make_profile.repository.candidates.CandidateCollegeProjectRepository;
import com.make_profile.repository.candidates.CandidateExperienceRepository;
import com.make_profile.repository.candidates.CandidateImageRepository;
import com.make_profile.repository.candidates.CandidateProjectRepository;
import com.make_profile.repository.candidates.CandidateQualificationRepository;
import com.make_profile.repository.candidates.CandidatesRepository;
import com.make_profile.repository.templates.TemplateAppliedRepository;
import com.make_profile.repository.templates.UsedTemplateRepository;
import com.make_profile.service.candidates.CandidateService;
import com.make_profile.service.candidates.TemplateService;

import jakarta.transaction.Transactional;

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

	@Autowired
	CandidateImageRepository candidateImageRepository;

	@Autowired
	CandidateCollegeProjectRepository candidateCollegeProjectRepository;

	@Override
	public CandidateDto createCandidate(CandidateDto candidateDto) {

		logger.debug("Service :: createResumeTemplate :: Entered");

		CandidateDto candidateResponseDto = null;

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

			if (Objects.nonNull(candidateDto.getExperiences())
					&& !CollectionUtils.isEmpty(candidateDto.getExperiences())) {
				List<CandidateExperienceEntity> convertCandidateExpeienceToEntity = convertCandidateExpeienceToEntity(
						candidateDto, convertDtoToEntity);

				if (Objects.nonNull(convertCandidateExpeienceToEntity)
						&& !CollectionUtils.isEmpty(convertCandidateExpeienceToEntity)) {
					convertDtoToEntity.setExperiences(convertCandidateExpeienceToEntity);
				}
			}

			if (Objects.nonNull(candidateDto.getQualification())
					&& !CollectionUtils.isEmpty(candidateDto.getQualification())) {
				List<CandidateQualificationEntity> convertCandidateQualificationToEntity = convertCandidateQualificationToEntity(
						candidateDto, convertDtoToEntity);

				if (Objects.nonNull(convertCandidateQualificationToEntity)
						&& !CollectionUtils.isEmpty(convertCandidateQualificationToEntity)) {
					convertDtoToEntity.setQualification(convertCandidateQualificationToEntity);
				}
			}

			if (Objects.nonNull(candidateDto.getCertificates())
					&& !CollectionUtils.isEmpty(candidateDto.getCertificates())) {
				List<CandidateCertificateEntity> convertCandidateCertificateToEntity = convertCandidateCertificateToEntity(
						candidateDto, convertDtoToEntity);

				if (Objects.nonNull(convertCandidateCertificateToEntity)
						&& !CollectionUtils.isEmpty(convertCandidateCertificateToEntity)) {
					convertDtoToEntity.setCertificates(convertCandidateCertificateToEntity);
				}
			}

			if (Objects.nonNull(candidateDto.getAchievements())
					&& !CollectionUtils.isEmpty(candidateDto.getAchievements())) {
				List<CandidateAchievementsEntity> convertCandidateAchievementsToEntity = convertCandidateAchievementsToEntity(
						candidateDto, convertDtoToEntity);

				if (Objects.nonNull(convertCandidateAchievementsToEntity)
						&& !CollectionUtils.isEmpty(convertCandidateAchievementsToEntity)) {
					convertDtoToEntity.setAchievements(convertCandidateAchievementsToEntity);
				}
			}

			if (Objects.nonNull(candidateDto.getCollegeProject())
					&& !CollectionUtils.isEmpty(candidateDto.getCollegeProject())) {

				List<CandidateCollegeProjectEntity> convertCandidateCollegeProjectDtoToEntity = convertCandidateCollegeProjectDtoToEntity(
						candidateDto, convertDtoToEntity);

				if (Objects.nonNull(convertCandidateCollegeProjectDtoToEntity)
						&& !CollectionUtils.isEmpty(convertCandidateCollegeProjectDtoToEntity)) {
					convertDtoToEntity.setCollegeProject(convertCandidateCollegeProjectDtoToEntity);

				}

			}

			CandidateEntity candidateEntity = candidatesRepository.save(convertDtoToEntity);

			UsedTemplateEntity usedTemplate = new UsedTemplateEntity();

			usedTemplate.setCandidateId(candidateEntity.getId());

			UsedTemplateEntity usedTemplateEntity = usedTemplateRepository.save(usedTemplate);

			templateService.saveCandidateDataInTemplate(usedTemplateEntity, candidateDto);

			usedTemplate = null;

			CandidateEntity candidate = candidatesRepository.findById(candidateEntity.getId()).get();

			candidateResponseDto = convertCandidateEntityToDto(candidate);

			candidateEntity = null;

		} catch (Exception e) {
			logger.debug("Service :: createResumeTemplate :: Exception" + e.getMessage());
		}
		logger.debug("Service :: createResumeTemplate :: Entered");
		return candidateResponseDto;
	}

	public CandidateEntity convertCandidateDtoToEntity(CandidateDto candidateDto) {
		logger.debug("Service :: convertCandidateDtoToEntity :: Entered");
		CandidateEntity candidateEntity = new CandidateEntity();
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

			if (Objects.nonNull(candidateDto.getCoreCompentencies())) {
				String coreCompentencies = candidateDto.getCoreCompentencies().stream()
						.collect(Collectors.joining(","));
				candidateEntity.setCoreCompentencies(coreCompentencies);
			}

			if (Objects.nonNull(candidateDto.getSoftSkills())) {
				String softSkills = candidateDto.getSoftSkills().stream().collect(Collectors.joining(","));
				candidateEntity.setSoftSkills(softSkills);
			}

			candidateEntity.setCreatedUserName(candidateDto.getCreatedUserName());

			if (candidateDto.getId() == null) {
				candidateEntity.setCreatedUser(candidateDto.getCreatedUser());
				candidateEntity.setCreatedDate(LocalDateTime.now());
			} else {
				candidateEntity.setModifiedUser(candidateDto.getCreatedUser());
				candidateEntity.setModifiedUser(candidateDto.getCreatedUser());
			}

		} catch (Exception e) {
			logger.error("Service :: convertCandidateDtoToEntity :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: convertCandidateDtoToEntity :: Exited");
		return candidateEntity;
	}

	public CandidateDto convertCandidateEntityToDto(CandidateEntity candidateEntity) {
		logger.debug("Service :: convertCandidateDtoToEntity :: Entered");
		CandidateDto candidateResponseDto = new CandidateDto();
		try {
			candidateResponseDto = modelMapper.map(candidateEntity, CandidateDto.class);

			candidateResponseDto.setLanguagesKnown(Arrays.asList(candidateEntity.getLanguagesKnown().split(",")));

			candidateResponseDto.setSkills(Arrays.asList(candidateEntity.getSkills().split(",")));

			if (Objects.nonNull(candidateEntity.getSoftSkills()) && !candidateEntity.getSoftSkills().isEmpty()) {
				candidateResponseDto.setSoftSkills(Arrays.asList(candidateEntity.getSoftSkills().split(",")));
			}

			if (Objects.nonNull(candidateEntity.getCoreCompentencies())
					&& !candidateEntity.getCoreCompentencies().isEmpty()) {
				candidateResponseDto
						.setCoreCompentencies(Arrays.asList(candidateEntity.getCoreCompentencies().split(",")));
			}

			if (Objects.nonNull(candidateEntity.getExperiences())
					&& !CollectionUtils.isEmpty(candidateEntity.getExperiences())) {
				for (int i = 0; i < candidateEntity.getExperiences().size(); i++) {
					String responsibilities = candidateEntity.getExperiences().get(i).getResponsibilities();
					candidateResponseDto.getExperiences().get(i)
							.setResponsibilities(Arrays.asList(responsibilities.split(",")));
				}
			}

			if (Objects.nonNull(candidateEntity.getExperiences().get(0).getProjects())
					&& !CollectionUtils.isEmpty(candidateEntity.getExperiences().get(0).getProjects())) {

				for (int i = 0; i < candidateEntity.getExperiences().size(); i++) {
					List<CandidateProjectEntity> projectsList = candidateEntity.getExperiences().get(i).getProjects();

					for (int j = 0; j < projectsList.size(); j++) {
						String projectSkills = projectsList.get(j).getProjectSkills();
						candidateResponseDto.getExperiences().get(i).getProjects().get(j)
								.setProjectSkills(Arrays.asList(projectSkills.split(",")));

					}
				}
			}

			if (Objects.nonNull(candidateEntity.getCollegeProject())
					&& !CollectionUtils.isEmpty(candidateEntity.getCollegeProject())) {
				for (int i = 0; i < candidateEntity.getCollegeProject().size(); i++) {
					String collegeProjectSkills = candidateEntity.getCollegeProject().get(i).getCollegeProjectSkills();
					candidateResponseDto.getCollegeProject().get(i)
							.setCollegeProjectSkills(Arrays.asList(collegeProjectSkills.split(",")));
				}
			}

		} catch (Exception e) {
			logger.error("Service :: convertCandidateDtoToEntity :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: convertCandidateDtoToEntity :: Exited");
		return candidateResponseDto;
	}

	public List<CandidateExperienceEntity> convertCandidateExpeienceToEntity(CandidateDto candidateDto,
			CandidateEntity candidateEntity) {
		logger.debug("Service :: convertCandidateExpeienceToEntity :: Entered");
		List<CandidateExperienceEntity> candidateExperienceList = new ArrayList<>();
		List<CandidateProjectEntity> candidateProjectDetailsList = new ArrayList<>();

		try {

			if (Objects.nonNull(candidateDto.getExperiences()) && !candidateDto.getExperiences().isEmpty()) {

				candidateDto.getExperiences().stream().forEach(experience -> {

					if (Objects.isNull(experience.getId())) {

						CandidateExperienceEntity candidateExperience = modelMapper.map(experience,
								CandidateExperienceEntity.class);

						candidateExperience.setCandidateId(candidateEntity);
						if (Objects.nonNull(experience.getResponsibilities())) {
							String responsibilities = experience.getResponsibilities().stream()
									.collect(Collectors.joining(","));
							candidateExperience.setResponsibilities(responsibilities);
						}

						if (Objects.nonNull(experience.getProjects())) {

							for (CandidateProjectDetailsDto project : experience.getProjects()) {
								CandidateProjectEntity candidateProject = modelMapper.map(project,
										CandidateProjectEntity.class);

								if (Objects.nonNull(project.getProjectSkills())) {
									String projectSkills = project.getProjectSkills().stream()
											.collect(Collectors.joining(","));
									candidateProject.setProjectSkills(projectSkills);
								}

								candidateProject.setCandidateExperience(candidateExperience);
								candidateProjectDetailsList.add(candidateProject);

								candidateProject = null;
							}
						}
						candidateExperience.setProjects(candidateProjectDetailsList);

						candidateExperienceList.add(candidateExperience);

						candidateExperience = null;
					} else {
						if (Objects.nonNull(experience.getId())) {

							if (!experience.getIsDeleted()) {
								if (Objects.nonNull(experience.getId())) {
									CandidateExperienceEntity exp = modelMapper.map(experience,
											CandidateExperienceEntity.class);

									exp.setCandidateId(candidateEntity);

									if (Objects.nonNull(exp.getResponsibilities())) {
										String responsibilities = experience.getResponsibilities().stream()
												.collect(Collectors.joining(","));
										exp.setResponsibilities(responsibilities);
									}

									if (Objects.nonNull(experience.getProjects())) {

										experience.getProjects().stream().forEach(project -> {

											if (!project.isProjectDeleted()) {
												if (Objects.isNull(project.getId())) {
													CandidateProjectEntity candidateProjectEntity = modelMapper
															.map(project, CandidateProjectEntity.class);

													if (Objects.nonNull(project.getProjectSkills())) {
														String projectSkills = project.getProjectSkills().stream()
																.collect(Collectors.joining(","));
														candidateProjectEntity.setProjectSkills(projectSkills);
													}

													candidateProjectEntity.setCandidateExperience(exp);
													candidateProjectDetailsList.add(candidateProjectEntity);
													exp.setProjects(candidateProjectDetailsList);

													candidateProjectEntity = null;
												} else {
													CandidateProjectEntity projectById = candidateProjectRepository
															.getProjectById(project.getId());
													projectById.setId(project.getId());
													projectById.setProjectDescription(project.getProjectDescription());
													projectById.setProjectName(project.getProjectName());
													projectById.setProjectRole(project.getProjectRole());

													if (Objects.nonNull(project.getProjectSkills())) {
														String projectSkills = project.getProjectSkills().stream()
																.collect(Collectors.joining(","));
														projectById.setProjectSkills(projectSkills);
													}

													projectById.setId(project.getId());
													projectById.setCandidateExperience(exp);
													candidateProjectDetailsList.add(projectById);
													exp.setProjects(candidateProjectDetailsList);

													projectById = null;

												}
											} else {
												candidateProjectRepository.deleteById(project.getId());
											}
										});

									}

									candidateExperienceList.add(exp);
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
		return candidateExperienceList;
	}

	public List<CandidateQualificationEntity> convertCandidateQualificationToEntity(CandidateDto candidateDto,
			CandidateEntity candidateEntity) {
		logger.debug("Service :: convertCandidateQualificationToEntity :: Entered");

		List<CandidateQualificationEntity> candidateQualificationEntityList = new ArrayList<>();
		try {

			if (Objects.nonNull(candidateDto.getQualification()) && !candidateDto.getQualification().isEmpty()) {

				candidateDto.getQualification().stream().forEach(qualification -> {

					if (Objects.isNull(qualification.getId())) {

						CandidateQualificationEntity candidateQualification = modelMapper.map(qualification,
								CandidateQualificationEntity.class);

						candidateQualification.setCandidateId(candidateEntity);

						candidateQualificationEntityList.add(candidateQualification);
					} else {
						if (Objects.nonNull(qualification.getId())) {
							CandidateQualificationEntity qualificationById = candidateQualificationRepository
									.getQualificationById(qualification.getId());

							if (!qualification.getIsDeleted()) {

								if (Objects.nonNull(qualificationById)) {

									qualificationById.setId(qualification.getId());
									qualificationById.setInstutionName(qualification.getInstutionName());
									qualificationById
											.setQualificationStartYear(qualification.getQualificationStartYear());
									qualificationById.setQualificationEndYear(qualification.getQualificationEndYear());

									qualificationById.setCandidateId(candidateEntity);
									qualificationById.setFieldOfStudy(qualification.getFieldOfStudy());

									candidateQualificationEntityList.add(qualificationById);

									qualificationById = null;
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
		return candidateQualificationEntityList;
	}

	public List<CandidateCertificateEntity> convertCandidateCertificateToEntity(CandidateDto candidateDto,
			CandidateEntity candidateEntity) {
		logger.debug("Service :: convertCandidateCertificateToEntity :: Entered");

		List<CandidateCertificateEntity> candidateCertificateEntityList = new ArrayList<>();

		try {
			if (Objects.nonNull(candidateDto.getCertificates()) && !candidateDto.getCertificates().isEmpty()) {

				candidateDto.getCertificates().stream().forEach(certificate -> {

					if (Objects.isNull(certificate.getId()) || certificate.getId().equals("")) {
						CandidateCertificateEntity candidateCertificate = modelMapper.map(certificate,
								CandidateCertificateEntity.class);

						candidateCertificate.setCandidateId(candidateEntity);
						candidateCertificateEntityList.add(candidateCertificate);
					} else {
						CandidateCertificateEntity certificateById = candidateCertificateRepository
								.getCertificateById(certificate.getId());

						if (!certificate.getIsDeleted()) {

							if (Objects.nonNull(certificateById)) {
								certificateById.setId(certificate.getId());
								certificateById.setCourseName(certificate.getCourseName());
								certificateById.setCourseStartDate(certificate.getCourseStartDate());
								certificateById.setCourseEndDate(certificate.getCourseEndDate());

								certificateById.setCandidateId(candidateEntity);
								candidateCertificateEntityList.add(certificateById);

								certificateById = null;
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
		return candidateCertificateEntityList;
	}

	public List<CandidateAchievementsEntity> convertCandidateAchievementsToEntity(CandidateDto candidateDto,
			CandidateEntity candidateEntity) {
		logger.debug("Service :: convertCandidateAchievementsToEntity :: Entered");

		List<CandidateAchievementsEntity> candidateAchievementsEntityList = new ArrayList<>();
		try {
			if (Objects.nonNull(candidateDto.getAchievements()) && !candidateDto.getAchievements().isEmpty()) {

				candidateDto.getAchievements().stream().forEach(achievements -> {

					if (Objects.isNull(achievements.getId())) {
						CandidateAchievementsEntity candidateAchievement = modelMapper.map(achievements,
								CandidateAchievementsEntity.class);

						candidateAchievement.setCandidateId(candidateEntity);
						candidateAchievementsEntityList.add(candidateAchievement);

					} else {
						CandidateAchievementsEntity achievementsById = candidateAchievementsRepository
								.getAchievementsById(achievements.getId());

						if (!achievements.getIsDeleted()) {
							if (Objects.nonNull(achievementsById)) {

								achievementsById.setId(achievements.getId());
								achievementsById.setAchievementsName(achievements.getAchievementsName());
								achievementsById.setAchievementsDate(achievements.getAchievementsDate());
								achievementsById.setCandidateId(candidateEntity);

								candidateAchievementsEntityList.add(achievementsById);

								achievementsById = null;
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
		return candidateAchievementsEntityList;
	}

	public List<CandidateCollegeProjectEntity> convertCandidateCollegeProjectDtoToEntity(CandidateDto candidateDto,
			CandidateEntity candidateEntity) {
		logger.debug("Service :: convertCandidateCollegeProjectDtoToEntity :: Entered");

		List<CandidateCollegeProjectEntity> candidateCollegeProjectEntityList = new ArrayList<>();

		try {
			if (Objects.nonNull(candidateDto.getCollegeProject()) && !candidateDto.getCollegeProject().isEmpty()) {

				candidateDto.getCollegeProject().stream().forEach(collegeProject -> {

					if (Objects.isNull(collegeProject.getId()) || collegeProject.getId() > 0) {
						CandidateCollegeProjectEntity candidateProject = modelMapper.map(collegeProject,
								CandidateCollegeProjectEntity.class);

						candidateProject.setCandidateId(candidateEntity);
						candidateCollegeProjectEntityList.add(candidateProject);
					} else {
						CandidateCollegeProjectEntity collegeProjectById = candidateCollegeProjectRepository
								.getCollegeProjectById(collegeProject.getId());

						if (!collegeProject.getIsDeleted()) {

							if (Objects.nonNull(collegeProjectById)) {
								collegeProjectById.setId(collegeProject.getId());
								collegeProjectById
										.setCollegeProjectDescription(collegeProject.getCollegeProjectDescription());

								String collegeProjectSkills = collegeProject.getCollegeProjectSkills().stream()
										.collect(Collectors.joining(","));
								collegeProjectById.setCollegeProjectSkills(collegeProjectSkills);

								collegeProjectById.setCandidateId(candidateEntity);
								candidateCollegeProjectEntityList.add(collegeProjectById);

								collegeProjectById = null;
							}
						} else {
							candidateCollegeProjectRepository.deleteById(collegeProject.getId());
							templateAppliedRepository.deleteByCandidateSectionId(candidateEntity.getId(),
									"COLLEGE PROJECTS", collegeProject.getId());
						}
					}
				});
			}
		} catch (Exception e) {
			logger.error("Service :: convertCandidateCollegeProjectDtoToEntity :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: convertCandidateCollegeProjectDtoToEntity :: Exited");
		return candidateCollegeProjectEntityList;
	}

	@Override
	public CandidateDto getCandidateById(String userName) {
		logger.debug("Service :: getCandidateById :: Entered");

		CandidateDto candidateResponseDto = null;
		CandidateEntity candidateEntity = null;
		try {
			candidateEntity = candidatesRepository.getCandidateByUserName(userName);
			if (Objects.nonNull(candidateEntity)) {
				candidateResponseDto = convertCandidateEntityToDto(candidateEntity);
			}
			candidateEntity = null;
		} catch (Exception e) {
			logger.error("Service :: getCandidateById :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: getCandidateById :: Exited");
		return candidateResponseDto;
	}

	@Override
	public byte[] uploadCandidateImage(CandidateImageDto candidateImageDto) {
		logger.debug("Service :: uploadCandidateImage :: Entered");

		CandidateImageEntity candidateImageEntity = null;
		CandidateImageEntity candidateImage = new CandidateImageEntity();
		CandidateImageEntity candidateId = null;

		try {
			Optional<CandidateImageEntity> findById = candidateImageRepository
					.findById(candidateImageDto.getCandidateId());

			boolean present = findById.isPresent();

			if (present) {
				candidateImageEntity = findById.get();
				candidateImageEntity.setImage(candidateImageDto.getAttachment().getBytes());
				candidateId = candidateImageRepository.save(candidateImageEntity);
			} else {
				candidateImage.setCandidateId(candidateImageDto.getCandidateId());
				candidateImage.setImage(candidateImageDto.getAttachment().getBytes());
				candidateId = candidateImageRepository.save(candidateImage);
			}
			candidateImageEntity = null;
			candidateImage = null;
		} catch (Exception e) {
			logger.error("Service :: uploadCandidateImage :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: uploadCandidateImage :: Exited");
		return candidateId.getImage();
	}

	@Override
	public byte[] getCandidateImage(CandidateImageDto candidateImageDto) {
		logger.debug("Service :: getCandidateImage :: Entered");

		CandidateImageEntity imageByCandidateId = null;
		try {
			imageByCandidateId = candidateImageRepository.getImageByCandidateId(candidateImageDto.getCandidateId());

			if (Objects.nonNull(imageByCandidateId)) {
				return imageByCandidateId.getImage();
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("Service :: getCandidateImage :: Exception :: " + e.getMessage());
			return null;
		}

	}

}
