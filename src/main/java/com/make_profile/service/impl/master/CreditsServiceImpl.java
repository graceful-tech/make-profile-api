package com.make_profile.service.impl.master;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.make_profile.dto.master.CreditsDto;
import com.make_profile.entity.master.CreditHistoryEntity;
import com.make_profile.entity.master.CreditsEntity;
import com.make_profile.repository.master.CreditHistoryRepository;
import com.make_profile.repository.master.CreditsRepository;
import com.make_profile.service.master.CreditsService;

@Service
public class CreditsServiceImpl implements CreditsService {

	private static final Logger logger = LoggerFactory.getLogger(CreditsServiceImpl.class);

	@Autowired
	CreditsRepository creditsRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	CreditHistoryRepository creditHistoryRepository;

	// TODO candidate comes from login
	@Override
	public List<CreditsDto> getCredits(Long userId) {
		logger.debug("Service :: getCredits :: Entered");

		List<CreditsDto> creditsDto = new ArrayList<>();
		List<CreditsEntity> findCreditsByUserIdAsList = new ArrayList<>();
		try {
			findCreditsByUserIdAsList = creditsRepository.findCreditsByUserIdAsList(userId);

			if (Objects.nonNull(findCreditsByUserIdAsList) && !CollectionUtils.isEmpty(findCreditsByUserIdAsList)) {

				findCreditsByUserIdAsList.forEach(credit -> {
					CreditsDto credits = new CreditsDto();
					credits = modelMapper.map(credit, CreditsDto.class);
					creditsDto.add(credits);
					credits = null;
				});
			}
			findCreditsByUserIdAsList = null;

		} catch (Exception e) {
			logger.error("Service :: getCredits :: Exception" + e.getMessage());
		}
		logger.debug("Service :: getCredits :: Exited");
		return creditsDto;
	}

	// TODO candidate comes from login
	@Override
	public boolean addCredits(CreditsDto creditsDto) {

		logger.debug("Service :: addCredits :: Entered");

		boolean status = false;
		CreditsEntity findCreditesByUserId = null;
		CreditsEntity creditEntity = null;
		try {
			findCreditesByUserId = creditsRepository.findCreditsByUserIdAndTemplateName(creditsDto.getUserId(),
					creditsDto.getTemplateName());

			if (Objects.nonNull(findCreditesByUserId)) {
				findCreditesByUserId.setUserId(creditsDto.getUserId());
				Double CreditAvailable = findCreditesByUserId.getCreditAvailable() == null ? 0.0
						: findCreditesByUserId.getCreditAvailable();
				findCreditesByUserId
						.setCreditAvailable(CreditAvailable + Double.valueOf(creditsDto.getCreditAvailable()));
				findCreditesByUserId.setId(findCreditesByUserId.getId());
				findCreditesByUserId.setTemplateName(creditsDto.getTemplateName());
				findCreditesByUserId.setPaymentDate(LocalDate.now());

				creditEntity = creditsRepository.save(findCreditesByUserId);

				findCreditesByUserId = null;
				status = true;
			} else {
				CreditsEntity candidateEntity = new CreditsEntity();

				candidateEntity.setUserId(creditsDto.getUserId());
				candidateEntity.setCreditAvailable(Double.valueOf(creditsDto.getCreditAvailable()));
				candidateEntity.setPaymentDate(LocalDate.now());
				candidateEntity.setTemplateName(creditsDto.getTemplateName());

				creditEntity = creditsRepository.save(candidateEntity);

				candidateEntity = null;
				status = true;
			}

			saveCreditHistory(creditsDto, creditEntity.getId());

			findCreditesByUserId = null;
			creditEntity = null;

		} catch (Exception e) {
			logger.error("Service :: addCredits :: Exception" + e.getMessage());
		}
		logger.debug("Service :: addCredits :: Exited");
		return status;
	}

	// TODO candidate comes from login
	@Override
	public boolean useCredit(CreditsDto creditsDto) {
		logger.debug("Service :: useCredit :: Entered");

		CreditsEntity findCreditesByUserId = null;
		boolean status = false;
		try {

			findCreditesByUserId = creditsRepository.findCreditsByUserIdAndTemplateName(creditsDto.getUserId(),
					creditsDto.getTemplateName());

			if (Objects.nonNull(findCreditesByUserId)) {
				if (findCreditesByUserId.getCreditAvailable() >= 1.0) {

					findCreditesByUserId.setUserId(creditsDto.getUserId());
					findCreditesByUserId
							.setCreditAvailable(findCreditesByUserId.getCreditAvailable() - Double.valueOf(1));
					Double creditUsed = findCreditesByUserId.getCreditUsed() == null ? 0.0
							: findCreditesByUserId.getCreditUsed();
					findCreditesByUserId.setCreditUsed(creditUsed + Double.valueOf(1));
					findCreditesByUserId.setTemplateName(findCreditesByUserId.getTemplateName());
					findCreditesByUserId.setId(findCreditesByUserId.getId());

					creditsRepository.save(findCreditesByUserId);

					status = true;
				}
			}

			findCreditesByUserId = null;
		} catch (Exception e) {
			logger.error("Service :: useCredit :: Exception" + e.getMessage());
		}
		logger.debug("Service :: useCredit :: Exited");
		return status;
	}

	@Override
	public Long getAvailableCredits(String templateName, Long userId) {
		logger.debug("Service :: getAvailableCredits :: Entered");

		Double availableCredits = null;
		Long available = null;
		try {

			availableCredits = creditsRepository.getAvailableCreditsByTemplateName(userId, templateName);

			if (Objects.nonNull(availableCredits)) {
				available = Math.round(availableCredits);
			} else {
				available = 0L;
			}

		} catch (Exception e) {
			logger.error("Service :: getAvailableCredits :: Exception" + e.getMessage());
		}
		logger.debug("Service :: getAvailableCredits :: Exited");
		return available;
	}

	public void saveCreditHistory(CreditsDto creditsDto, Long creditId) {
		logger.debug("Service :: saveCreditHistory :: Entered");

		try {
			CreditHistoryEntity creditHistoryEntity = new CreditHistoryEntity();

			creditHistoryEntity.setPaidAmount(creditsDto.getCreditAvailable());
			creditHistoryEntity.setPaymentDate(LocalDate.now());
			creditHistoryEntity.setTemplateName(creditsDto.getTemplateName());
			creditHistoryEntity.setCreditId(creditId);
			creditHistoryEntity.setUserId(creditsDto.getUserId());

			creditHistoryRepository.save(creditHistoryEntity);

			creditHistoryEntity = null;
		}

		catch (Exception e) {
			logger.error("Service :: saveCreditHistory :: Exception" + e.getMessage());
		}
		logger.debug("Service :: saveCreditHistory :: Exited");
	}

}
