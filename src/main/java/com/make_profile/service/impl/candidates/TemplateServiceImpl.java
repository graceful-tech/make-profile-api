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
import com.make_profile.entity.templates.TemplateAppliedEntity;
import com.make_profile.entity.templates.UsedTemplateEntity;
import com.make_profile.repository.templates.TemplateAppliedRepository;
import com.make_profile.repository.templates.TemplateRepository;
import com.make_profile.service.candidates.TemplateService;

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

}
