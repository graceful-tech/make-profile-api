package com.make_profile.service.impl.candidates;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.make_profile.dto.candidates.CandidateDto;
import com.make_profile.entity.common.FieldCheckerDto;
import com.make_profile.entity.templates.TemplateAppliedEntity;
import com.make_profile.entity.templates.TemplateEntity;
import com.make_profile.entity.templates.UsedTemplateEntity;
import com.make_profile.repository.templates.TemplateAppliedRepository;
import com.make_profile.repository.templates.TemplateRepository;
import com.make_profile.service.candidates.TemplateService;

import io.jsonwebtoken.lang.Arrays;

@Service
public class TemplateServiceImpl implements TemplateService {

	private static final Logger logger = LoggerFactory.getLogger(TemplateServiceImpl.class);

	@Autowired
	TemplateRepository templateRepository;

	@Autowired
	TemplateAppliedRepository templateAppliedRepository;

	@Override
	public void saveCandidateDataInTemplate(UsedTemplateEntity usedTemplateEntity, CandidateDto candidateDto) {
		logger.debug("Service :: saveCandidateDataInTemplate :: Entered");

		try {

			List<TemplateAppliedEntity> templateAppliedEnity = new ArrayList<>();

			if (Objects.nonNull(candidateDto.getExperiences())) {
				candidateDto.getExperiences().forEach(exp -> {

					if (!exp.getIsDeleted()) {
						Long i = 0L;
						TemplateAppliedEntity experience = new TemplateAppliedEntity();

						experience.setCandidateId(usedTemplateEntity.getCandidateId());
						experience.setUsedTemplateId(usedTemplateEntity.getId());
						experience.setSectionName("EXPERIENCE");
						experience.setSectionId(i++);
						// experience.setVisible(true);
						templateAppliedEnity.add(experience);

						experience = null;
					}
				});

			}

			if (Objects.nonNull(candidateDto.getQualification())) {
				candidateDto.getQualification().forEach(qualifi -> {

					if (!qualifi.getIsDeleted()) {
						Long i = 0L;
						TemplateAppliedEntity qualification = new TemplateAppliedEntity();

						qualification.setCandidateId(usedTemplateEntity.getCandidateId());
						qualification.setUsedTemplateId(usedTemplateEntity.getId());
						qualification.setSectionName("QUALIFICATION");
						qualification.setSectionId(i++);
						// experience.setVisible(true);
						templateAppliedEnity.add(qualification);

						qualification = null;
					}
				});
			}

			if (Objects.nonNull(candidateDto.getAchievements())) {
				candidateDto.getQualification().forEach(achieve -> {

					if (!achieve.getIsDeleted()) {
						Long i = 0L;
						TemplateAppliedEntity achievements = new TemplateAppliedEntity();

						achievements.setCandidateId(usedTemplateEntity.getCandidateId());
						achievements.setUsedTemplateId(usedTemplateEntity.getId());
						achievements.setSectionName("ACHIEVEMENTS");
						achievements.setSectionId(i++);
						// experience.setVisible(true);
						templateAppliedEnity.add(achievements);

						achievements = null;
					}
				});
			}

			if (Objects.nonNull(candidateDto.getCertificates())) {
				candidateDto.getQualification().forEach(certifi -> {

					if (!certifi.getIsDeleted()) {
						Long i = 0L;
						TemplateAppliedEntity ceritificates = new TemplateAppliedEntity();

						ceritificates.setCandidateId(usedTemplateEntity.getCandidateId());
						ceritificates.setUsedTemplateId(usedTemplateEntity.getId());
						ceritificates.setSectionName("CERTIFICATES");
						ceritificates.setSectionId(i++);
						// experience.setVisible(true);
						templateAppliedEnity.add(ceritificates);

						ceritificates = null;
					}
				});
			}

			if (Objects.nonNull(candidateDto.getCollegeProject())
					&& !CollectionUtils.isEmpty(candidateDto.getCollegeProject())) {
				candidateDto.getQualification().forEach(certifi -> {

					if (!certifi.getIsDeleted()) {
						Long i = 0L;
						TemplateAppliedEntity collegeProject = new TemplateAppliedEntity();

						collegeProject.setCandidateId(usedTemplateEntity.getCandidateId());
						collegeProject.setUsedTemplateId(usedTemplateEntity.getId());
						collegeProject.setSectionName("COLLEGE PROJECT");
						collegeProject.setSectionId(i++);
						// experience.setVisible(true);
						templateAppliedEnity.add(collegeProject);

						collegeProject = null;
					}
				});
			}

			templateAppliedRepository.saveAll(templateAppliedEnity);

		} catch (Exception e) {
			logger.error("Service :: saveCandidateDataInTemplate :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: saveCandidateDataInTemplate :: Exited");
	}

	@Override
	public FieldCheckerDto checkResumeTemplateFields(CandidateDto candidateDto) {
		logger.debug("Service :: checkResumeTemplateFields :: Entered");

		TemplateEntity templateByName = null;

		Long minSectionCount = 0L;

		Long count = 0L;

		Long remaningCount = 0L;

		// StringBuilder sectionNames = new StringBuilder();
		// String substring = new String();

		FieldCheckerDto fieldChecker = new FieldCheckerDto();

		List<String> fields = new ArrayList<>();

		try {

			templateByName = templateRepository.getTemplateByName(candidateDto.getResumeFormatName());

			if (Objects.nonNull(templateByName)) {
				minSectionCount = templateByName.getMinSectionCount();

				if (Objects.nonNull(candidateDto.getQualification())
						&& !CollectionUtils.isEmpty(candidateDto.getQualification())) {
					count++;
				}

				if (Objects.nonNull(candidateDto.getSkills()) && !candidateDto.getSkills().isEmpty()) {
					count++;
				}

				if (Objects.nonNull(candidateDto.getExperiences())
						&& !CollectionUtils.isEmpty(candidateDto.getExperiences())) {
					count++;
				}

				if ((Objects.nonNull(candidateDto.getSoftSkills()) && !candidateDto.getSoftSkills().isEmpty())
						|| (Objects.nonNull(candidateDto.getCoreCompentencies())
								&& !candidateDto.getCoreCompentencies().isEmpty())) {
					count++;
				}

				if (Objects.nonNull(candidateDto.getCertificates())
						&& !CollectionUtils.isEmpty(candidateDto.getCertificates())) {
					count++;
				}

				if (Objects.nonNull(candidateDto.getAchievements())
						&& !CollectionUtils.isEmpty(candidateDto.getAchievements())) {
					count++;
				}

				remaningCount = minSectionCount - count;

				if (remaningCount > 0) {
					fieldChecker.setCount(remaningCount);
				}

				// for getting the Mandatory Sections
				List<String> section = Arrays.asList(templateByName.getMandatorySectionName().split(","));

				if (section.contains("experience")) {
					if ((Objects.isNull(candidateDto.getExperiences())
							|| CollectionUtils.isEmpty(candidateDto.getExperiences()))
							&& candidateDto.isFresher() == false) {
						// sectionNames.append("experience,");
						fields.add("experience");
					}
				}

				if (section.contains("qualification")) {
					if (Objects.isNull(candidateDto.getQualification())
							|| CollectionUtils.isEmpty(candidateDto.getQualification())) {
						// sectionNames.append("qualification,");
						fields.add("qualification");
					}
				}

				if (section.contains("skills")) {
					if (Objects.isNull(candidateDto.getSkills()) || candidateDto.getSkills().isEmpty()) {
						// sectionNames.append("skills,");
						fields.add("skills");
					}
				}

				if (section.contains("achievements")) {
					if (Objects.isNull(candidateDto.getAchievements())
							|| CollectionUtils.isEmpty(candidateDto.getAchievements())) {
						// sectionNames.append("achievements,");
						fields.add("achievements");
					}
				}

				if (section.contains("course")) {
					if (Objects.isNull(candidateDto.getCertificates())
							|| CollectionUtils.isEmpty(candidateDto.getCertificates())) {
						// sectionNames.append("course,");
						fields.add("course");
					}
				}

				if (section.contains("extraSkills")) {
					if ((Objects.isNull(candidateDto.getSoftSkills()) || candidateDto.getSoftSkills().isEmpty())
							|| (Objects.isNull(candidateDto.getCoreCompentencies())
									|| candidateDto.getCoreCompentencies().isEmpty())) {
						// sectionNames.append("extraSkills,");
						fields.add("extraSkills");
					}
				}
				
				 

//				if (sectionNames != null && !sectionNames.isEmpty()) {
//					substring = sectionNames.substring(0, sectionNames.length() - 1);
//				}

				fieldChecker.setFieldName(fields);

				fields = null;

				// sectionNames = null;
				minSectionCount = null;
				count = null;
			}

		} catch (Exception e) {
			logger.error("Service :: checkResumeTemplateFields :: Exception :: " + e.getMessage());

		}
		logger.debug("Service :: checkResumeTemplateFields :: Exited");
		return fieldChecker;

	}

}
