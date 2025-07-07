package com.make_profile.service.impl.candidates;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.make_profile.dto.candidates.CandidateAdditionalDetailsDto;
import com.make_profile.dto.candidates.CandidateDto;
import com.make_profile.dto.candidates.CandidateImageDto;
import com.make_profile.entity.candidates.CandidateAdditionalDetailsEntity;
import com.make_profile.entity.candidates.CandidateEntity;
import com.make_profile.entity.candidates.CandidateImageEntity;
import com.make_profile.entity.history.candidates.CandidateHistoryEntity;
import com.make_profile.repository.candidates.CandidateAdditionalDetailsRepository;
import com.make_profile.repository.candidates.CandidateImageRepository;
import com.make_profile.repository.candidates.CandidatesRepository;
import com.make_profile.repository.common.EnvironmentRepository;
import com.make_profile.repository.history.candidates.CandidateHistoryRepository;
import com.make_profile.repository.templates.TemplateAppliedRepository;
import com.make_profile.repository.templates.UsedTemplateRepository;
import com.make_profile.repository.user.UserRepository;
import com.make_profile.service.candidates.CandidateService;
import com.make_profile.service.candidates.TemplateService;
import com.make_profile.utility.CommonConstants;
import com.make_profile.utility.CommonUtils;

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
	EnvironmentRepository environmentRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CandidateAdditionalDetailsRepository candidateAdditionalDetailsRepository;

	@Override
	public CandidateDto createCandidate(CandidateDto candidateDto, String Username) {
		logger.debug("Service :: createCandidate :: Entered");

		CandidateDto candidateResponseDto = null;
		CandidateEntity candidateEntity = new CandidateEntity();
		CandidateEntity candidateByUserName = null;

		try {

			candidateByUserName = candidatesRepository.getCandidateByUserName(Username);

			if (Objects.nonNull(candidateByUserName)) {

				if (Objects.isNull(candidateDto.getMobileNumber())) {
					candidateDto.setMobileNumber(candidateByUserName.getMobileNumber());
				}
				candidateEntity = modelMapper.map(candidateDto, CandidateEntity.class);
				candidateEntity.setId(candidateByUserName.getId());
				candidateEntity.setCreatedUserName(Username);
				candidateEntity.setModifiedDate(LocalDateTime.now());
				candidateEntity.setModifiedUser(candidateDto.getCreatedUser());
			} else {
				candidateEntity = modelMapper.map(candidateDto, CandidateEntity.class);
				candidateEntity.setCreatedUserName(Username);
				candidateEntity.setCreatedUser(candidateDto.getCreatedUser());
				candidateEntity.setCreatedDate(LocalDateTime.now());
				candidateEntity.setModifiedDate(LocalDateTime.now());
			}

//			if (Objects.nonNull(candidateByUserName)) {
//
//				if (Objects.nonNull(candidateDto.getCreatedUser())) {
//					candidateEntity.setModifiedUser(candidateDto.getCreatedUser());
//				}
//				candidateEntity.setModifiedDate(LocalDateTime.now());
//			} else {
//				
//			}

			CandidateEntity ResponceCandidateEntity = candidatesRepository.save(candidateEntity);

			// save candidate in history
			saveCandidateInHistory(candidateDto, Username, ResponceCandidateEntity.getId());

			saveCandidateInHurecomv2(candidateDto);

			candidateResponseDto = modelMapper.map(ResponceCandidateEntity, CandidateDto.class);

			ResponceCandidateEntity = null;
			candidateEntity = null;
			candidateByUserName = null;

		} catch (Exception e) {
			logger.debug("Service :: createCandidate :: Exception" + e.getMessage());
		}
		logger.debug("Service :: createCandidate :: Entered");
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
		byte[] candidateImage = null;
		try {

			String environmentValueByKey = environmentRepository
					.getEnvironmentValueByKey(CommonConstants.IMAGE_LOCATION);

			CandidateImageEntity candidateImageEntity = candidateImageRepository
					.getImageByCandidateId(candidateImageDto.getCandidateId());

			if (Objects.nonNull(candidateImageEntity)) {
				Path targetLocation = Paths.get(candidateImageEntity.getFileLocation());
				CommonUtils.deleteFileFromServer(targetLocation);
			}

			Path targetLocation = Paths.get(environmentValueByKey)
					.resolve(candidateImageDto.getCandidateId().toString());

			// copy the image to target location.
			String path = CommonUtils.moveImageFileToServer(candidateImageDto.getAttachment(), targetLocation);

			if (Objects.isNull(candidateImageEntity)) {
				candidateImageEntity = new CandidateImageEntity();
			}

			candidateImageEntity.setFileName(candidateImageDto.getAttachment().getOriginalFilename());
			candidateImageEntity.setFileLocation(path);
			candidateImageEntity.setCandidateId(candidateImageDto.getCandidateId());

			candidateImageRepository.save(candidateImageEntity);

			candidateImageEntity = null;

			candidateImage = getCandidateImage(candidateImageDto.getCandidateId());
		} catch (Exception e) {
			logger.error("Service :: uploadCandidateImage :: Exception :: " + e.getMessage());
			return null;
		}
		logger.debug("Service :: uploadCandidateImage :: Exited");
		return candidateImage;
	}

	@Override
	public byte[] getCandidateImage(Long candidateId) {
		logger.debug("Service :: getCandidateImage :: Entered");
		CandidateImageEntity imageByCandidateId = null;
		byte[] byteArray = null;
		try {
			imageByCandidateId = candidateImageRepository.getImageByCandidateId(candidateId);

			if (Objects.nonNull(imageByCandidateId)) {
				Path targetLocation = Paths.get(imageByCandidateId.getFileLocation());
				byteArray = CommonUtils.downloadFileFromServer(targetLocation);
			}

		} catch (Exception e) {
			logger.error("Service :: getCandidateImage :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: getCandidateImage :: Exited");
		return byteArray;

	}

	public void saveCandidateInHistory(CandidateDto candidateDto, String Username, Long responseId) {
		logger.debug("Service :: saveCandidateInHistory :: Entered");

		CandidateHistoryEntity candidateHistoryEntity = new CandidateHistoryEntity();
		try {
			if (Objects.nonNull(candidateDto.getExperiences())
					&& !CollectionUtils.isEmpty(candidateDto.getExperiences())) {
				candidateDto.getExperiences().forEach(exp -> {
					exp.setId(null);
					if (Objects.nonNull(exp.getProjects()) && !CollectionUtils.isEmpty(exp.getProjects())) {
						exp.getProjects().forEach(pro -> {
							pro.setId(null);
						});
					}
				});
			}

			if (Objects.nonNull(candidateDto.getCollegeProject())
					&& !CollectionUtils.isEmpty(candidateDto.getCollegeProject())) {
				candidateDto.getCollegeProject().forEach(collegeProject -> {
					collegeProject.setId(null);
				});
			}

			if (Objects.nonNull(candidateDto.getCertificates())
					&& !CollectionUtils.isEmpty(candidateDto.getCertificates())) {
				candidateDto.getCertificates().forEach(cer -> {
					cer.setId(null);
				});
			}

			if (Objects.nonNull(candidateDto.getQualification())
					&& !CollectionUtils.isEmpty(candidateDto.getQualification())) {
				candidateDto.getQualification().forEach(qua -> {
					qua.setId(null);
				});
			}

			if (Objects.nonNull(candidateDto.getAchievements())
					&& !CollectionUtils.isEmpty(candidateDto.getAchievements())) {
				candidateDto.getAchievements().forEach(ach -> {
					ach.setId(null);
				});
			}

			// To save the candidate in history
			candidateHistoryEntity = modelMapper.map(candidateDto, CandidateHistoryEntity.class);

			candidateHistoryEntity.setCreatedUserName(Username);
			candidateHistoryEntity.setCreatedUser(candidateDto.getCreatedUser());
			candidateHistoryEntity.setCandidateId(responseId);
			candidateHistoryEntity.setCreatedDate(LocalDateTime.now());
			candidateHistoryEntity.setModifiedDate(LocalDateTime.now());
			candidateHistoryEntity.setModifiedUser(candidateDto.getCreatedUser());

			candidateHistoryEntity.setId(null);

			candidateHistoryRepository.save(candidateHistoryEntity);

		} catch (Exception e) {
			logger.error("Service :: saveCandidateInHistory :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: saveCandidateInHistory :: Exited");

	}

	public void saveCandidateInHurecomv2(CandidateDto candidateDto) {
		logger.debug("Service :: saveCandidateInHurecomv2 :: Entered");

		try {

			if (candidatesRepository.findCandidateByMobileNumber(candidateDto.getMobileNumber()) == 1) {

				candidatesRepository.UpdateCandidateInHurecomV2(candidateDto.getName(), candidateDto.getMobileNumber(),
						candidateDto.getSkills(), candidateDto.getEmail(), candidateDto.isFresher(),
						candidateDto.getGender());
			} else {
				String candidateQualification = candidateDto.getQualification().get(0).getDepartment() != null
						? candidateDto.getQualification().get(0).getDepartment()
						: "NA";

				candidatesRepository.saveCandidateInHurecomV2(candidateDto.getName(), candidateDto.getMobileNumber(),
						candidateDto.getSkills(), candidateDto.getEmail(),
						candidateDto.getGender() != null ? candidateDto.getGender() : "Male", candidateQualification,
						candidateDto.isFresher());

				candidateQualification = null;
			}

		} catch (Exception e) {
			logger.error("Service :: saveCandidateInHurecomv2 :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: saveCandidateInHurecomv2 :: Exited");
	}

	@Override
	public boolean saveAdditionalDetails(CandidateAdditionalDetailsDto candidateAdditionalDetailsDto) {
		logger.debug("Service :: saveAdditionalDetails :: Entered");

		boolean status = false;

		CandidateAdditionalDetailsEntity detailsEntity = null;
		CandidateAdditionalDetailsEntity additionalDetails = null;

		try {
			if (Objects.nonNull(candidateAdditionalDetailsDto)) {
				additionalDetails = candidateAdditionalDetailsRepository
						.getAdditionalDetailsByMobileNumber(candidateAdditionalDetailsDto.getMobileNumber());

				if (Objects.nonNull(additionalDetails)) {
					detailsEntity = modelMapper.map(candidateAdditionalDetailsDto,
							CandidateAdditionalDetailsEntity.class);

					detailsEntity.setId(additionalDetails.getId());

					candidateAdditionalDetailsRepository.save(detailsEntity);

					updateHurecomV2CandidateDetails(candidateAdditionalDetailsDto);
				} else {
					detailsEntity = modelMapper.map(candidateAdditionalDetailsDto,
							CandidateAdditionalDetailsEntity.class);

					candidateAdditionalDetailsRepository.save(detailsEntity);

					updateHurecomV2CandidateDetails(candidateAdditionalDetailsDto);

				}
				status = true;
				additionalDetails = null;
				detailsEntity = null;
			}
		} catch (Exception e) {
			logger.error("Service :: saveAdditionalDetails :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: saveAdditionalDetails :: Exited");
		return status;
	}

	@Override
	public CandidateAdditionalDetailsDto getCandidateDetails(String mobile) {
		logger.debug("Service :: getCandidateDetails :: Entered");

		CandidateAdditionalDetailsEntity additionalDetails = null;
		CandidateAdditionalDetailsDto candidateAdditionalDetailsDto = null;
		try {
			additionalDetails = candidateAdditionalDetailsRepository.getAdditionalDetailsByMobileNumber(mobile);
			candidateAdditionalDetailsDto = modelMapper.map(additionalDetails, CandidateAdditionalDetailsDto.class);

			additionalDetails = null;
		} catch (Exception e) {
			logger.error("Service :: getCandidateDetails :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: getCandidateDetails :: Exited");
		return candidateAdditionalDetailsDto;
	}

	public void updateHurecomV2CandidateDetails(CandidateAdditionalDetailsDto candidateAdditionalDetailsDto) {
		logger.debug("Service :: updateHurecomV2CandidateDetails :: Entered");

		try {

			String mobileNumberById = candidatesRepository
					.getMobileNumberById(candidateAdditionalDetailsDto.getCandidateId());

			candidatesRepository.UpdateCandidateProfessionalDetailsInHurecomV2(

					candidateAdditionalDetailsDto.getPreferredLocation() != null
							? candidateAdditionalDetailsDto.getPreferredLocation()
							: "NA",
					candidateAdditionalDetailsDto.getRelevantExperience() != null
							? candidateAdditionalDetailsDto.getRelevantExperience()
							: 0,
					candidateAdditionalDetailsDto.getTotalWorkExperience() != null
							? candidateAdditionalDetailsDto.getTotalWorkExperience()
							: 0,
					candidateAdditionalDetailsDto.getCurrentCostToCompany() != null
							? candidateAdditionalDetailsDto.getCurrentCostToCompany()
							: 0,
					candidateAdditionalDetailsDto.getExpectedCostToCompany() != null
							? candidateAdditionalDetailsDto.getExpectedCostToCompany()
							: 0,
					candidateAdditionalDetailsDto.getCompanyName() != null
							? candidateAdditionalDetailsDto.getCompanyName()
							: "NA",
					mobileNumberById,
					candidateAdditionalDetailsDto.getQualification() != null
							? candidateAdditionalDetailsDto.getQualification()
							: "NA");

			mobileNumberById = null;

		} catch (Exception e) {
			logger.error("Service :: updateHurecomV2CandidateDetails :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: updateHurecomV2CandidateDetails :: Exited");

	}

}
