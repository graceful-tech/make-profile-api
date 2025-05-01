package com.make_profile.service.impl.candidates;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.make_profile.dto.candidates.CandidateDto;
import com.make_profile.dto.candidates.CandidateImageDto;
import com.make_profile.entity.candidates.CandidateEntity;
import com.make_profile.entity.candidates.CandidateImageEntity;
import com.make_profile.entity.history.candidates.CandidateHistoryEntity;
import com.make_profile.entity.history.candidates.CandidateImageHistoryEntity;
import com.make_profile.repository.candidates.CandidateImageRepository;
import com.make_profile.repository.candidates.CandidatesRepository;
import com.make_profile.repository.history.candidates.CandidateHistoryRepository;
import com.make_profile.repository.history.candidates.CandidateImageHistoryRepository;
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
	CandidateHistoryRepository candidateHistoryRepository;

	@Autowired
	CandidateImageHistoryRepository candidateImageHistoryRepository;

	@Override
	public CandidateDto createCandidate(CandidateDto candidateDto) {
		logger.debug("Service :: createResumeTemplate :: Entered");

		CandidateDto candidateResponseDto = null;
		CandidateEntity candidateEntity = new CandidateEntity();
		CandidateHistoryEntity candidateHistoryEntity = new CandidateHistoryEntity();

		try {
			candidateEntity = modelMapper.map(candidateDto, CandidateEntity.class);
			candidateEntity.setCreatedUserName(candidateDto.getCreatedUserName());

			if (Objects.nonNull(candidateDto.getId())) {
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

			// To save the candidate in history
			candidateHistoryEntity = modelMapper.map(candidateDto, CandidateHistoryEntity.class);
			candidateHistoryEntity.setCandidateId(ResponceCandidateEntity.getId());
			candidateHistoryRepository.save(candidateHistoryEntity);

//			UsedTemplateEntity usedTemplate = new UsedTemplateEntity();
//			usedTemplate.setCandidateId(candidateEntity.getId());
//			UsedTemplateEntity usedTemplateEntity = usedTemplateRepository.save(usedTemplate);
//			templateService.saveCandidateDataInTemplate(usedTemplateEntity, candidateDto);

			candidateResponseDto = modelMapper.map(ResponceCandidateEntity, CandidateDto.class);

//			usedTemplate = null;
			ResponceCandidateEntity = null;
			candidateEntity = null;
			candidateHistoryEntity = null;

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

		CandidateImageHistoryEntity candidateImageHistoryEntity = new CandidateImageHistoryEntity();

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

			// TODO
			candidateImageHistoryEntity.setImage(candidateImageDto.getAttachment().getBytes());
			candidateImageHistoryEntity.setCandidateId(candidateId.getId());
			candidateImageHistoryRepository.save(candidateImageHistoryEntity);

			candidateImageEntity = null;
			candidateImage = null;
			candidateImageHistoryEntity = null;
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
