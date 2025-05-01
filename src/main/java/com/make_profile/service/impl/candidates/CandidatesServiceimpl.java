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
import com.make_profile.entity.history.candidates.CandidateAchievementsHistoryEntity;
import com.make_profile.entity.history.candidates.CandidateHistoryEntity;
import com.make_profile.entity.templates.UsedTemplateEntity;
import com.make_profile.repository.candidates.CandidateAchievementsRepository;
import com.make_profile.repository.candidates.CandidateCertificateRepository;
import com.make_profile.repository.candidates.CandidateCollegeProjectRepository;
import com.make_profile.repository.candidates.CandidateExperienceRepository;
import com.make_profile.repository.candidates.CandidateImageRepository;
import com.make_profile.repository.candidates.CandidateProjectRepository;
import com.make_profile.repository.candidates.CandidateQualificationRepository;
import com.make_profile.repository.candidates.CandidatesRepository;
import com.make_profile.repository.history.candidates.CandidateHistoryRepository;
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

	@Autowired
	CandidateHistoryRepository candidateHistoryRepository;

	@Override
	public CandidateDto createCandidate(CandidateDto candidateDto) {
		logger.debug("Service :: createResumeTemplate :: Entered");

		CandidateDto candidateResponseDto = null;
		CandidateEntity candidateEntity = new CandidateEntity();

		try {
			candidateEntity = modelMapper.map(candidateDto, CandidateEntity.class);
			candidateEntity.setCreatedUserName(candidateDto.getCreatedUserName());

			if (candidateDto.getId() == null) {
				if (Objects.nonNull(candidateDto.getCreatedUser())) {
					candidateEntity.setCreatedUser(candidateDto.getCreatedUser());
				}
				candidateEntity.setCreatedDate(LocalDateTime.now());
			} else {
				if (Objects.nonNull(candidateDto.getCreatedUser())) {
					candidateEntity.setModifiedUser(candidateDto.getCreatedUser());
				}
				candidateEntity.setModifiedDate(LocalDateTime.now());
			}

			CandidateEntity ResponceCandidateEntity = candidatesRepository.save(candidateEntity);

			UsedTemplateEntity usedTemplate = new UsedTemplateEntity();
			usedTemplate.setCandidateId(candidateEntity.getId());
			UsedTemplateEntity usedTemplateEntity = usedTemplateRepository.save(usedTemplate);
			templateService.saveCandidateDataInTemplate(usedTemplateEntity, candidateDto);

			usedTemplate = null;

			candidateResponseDto = modelMapper.map(ResponceCandidateEntity, CandidateDto.class);

			ResponceCandidateEntity = null;
			candidateEntity = null;

		} catch (Exception e) {
			logger.debug("Service :: createResumeTemplate :: Exception" + e.getMessage());
		}
		logger.debug("Service :: createResumeTemplate :: Entered");
		return candidateResponseDto;
	}

	@Override
	public CandidateDto getCandidateById(String userName) {
		logger.debug("Service :: getCandidateById :: Entered");

		CandidateDto candidateResponseDto = null;
		CandidateEntity candidateEntity = null;
		try {
			candidateEntity = candidatesRepository.getCandidateByUserName(userName);
			if (Objects.nonNull(candidateEntity)) {
				candidateResponseDto = modelMapper.map(candidateEntity, CandidateDto.class);
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
	public byte[] getCandidateImage(Long candidateId) {
		logger.debug("Service :: getCandidateImage :: Entered");

		CandidateImageEntity imageByCandidateId = null;
		try {
			imageByCandidateId = candidateImageRepository.getImageByCandidateId(candidateId);

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
