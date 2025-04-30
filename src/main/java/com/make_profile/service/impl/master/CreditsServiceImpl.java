package com.make_profile.service.impl.master;

import java.time.LocalDate;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.make_profile.dto.master.CreditsDto;
import com.make_profile.entity.master.CreditsEntity;
import com.make_profile.repository.master.CreditsRepository;
import com.make_profile.service.master.CreditsService;

@Service
public class CreditsServiceImpl implements CreditsService {

	private static final Logger logger = LoggerFactory.getLogger(CreditsServiceImpl.class);

	@Autowired
	CreditsRepository creditsRepository;

	@Autowired
	ModelMapper modelMapper;

	// TODO candidate comes from login
	@Override
	public CreditsDto getCredits(Long userId) {
		logger.debug("Service :: getCredits :: Entered");

		CreditsDto creditsDto = null;
		try {
			CreditsEntity creditsEntity = creditsRepository.findCreditsByUserId(userId);

			if (Objects.nonNull(creditsEntity)) {
				creditsDto = modelMapper.map(creditsEntity, CreditsDto.class);
			}
		} catch (Exception e) {
			logger.debug("Service :: getCredits :: Exception" + e.getMessage());
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
		try {
			findCreditesByUserId = creditsRepository.findCreditsByUserId(creditsDto.getUserId());

			if (Objects.nonNull(findCreditesByUserId)) {
				
				findCreditesByUserId.setUserId(creditsDto.getUserId());
				Double CreditAvailable = findCreditesByUserId.getCreditAvailable() == null ? 0.0: findCreditesByUserId.getCreditAvailable();
				findCreditesByUserId.setCreditAvailable(CreditAvailable + Double.valueOf(creditsDto.getCreditAvailable()));
				findCreditesByUserId.setId(findCreditesByUserId.getId());
				findCreditesByUserId.setPaymentDate(LocalDate.now());

				creditsRepository.save(findCreditesByUserId);

				findCreditesByUserId = null;
				status = true;
			} else {
				CreditsEntity candidateEntity = new CreditsEntity();

				candidateEntity.setCandidateId(creditsDto.getCandidateId());
				candidateEntity.setCreditAvailable(Double.valueOf(creditsDto.getCreditAvailable()));
				candidateEntity.setPaymentDate(LocalDate.now());
				creditsRepository.save(candidateEntity);

				candidateEntity = null;
				status = true;
			}
			findCreditesByUserId = null;

		} catch (Exception e) {
			logger.debug("Service :: addCredits :: Exception" + e.getMessage());
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

			findCreditesByUserId = creditsRepository.findCreditsByUserId(creditsDto.getCandidateId());

			if (Objects.nonNull(findCreditesByUserId)) {
				if (findCreditesByUserId.getCreditAvailable() >= 2.0) {
					
					findCreditesByUserId.setUserId(creditsDto.getUserId());
					findCreditesByUserId.setCreditAvailable(findCreditesByUserId.getCreditAvailable() - Double.valueOf(2));
					Double creditUsed = findCreditesByUserId.getCreditUsed() == null ? 0.0: findCreditesByUserId.getCreditUsed();
					findCreditesByUserId.setCreditUsed(creditUsed + Double.valueOf(2));
					findCreditesByUserId.setId(findCreditesByUserId.getId());

					creditsRepository.save(findCreditesByUserId);

					status = true;
				}
			}

//				else {
//				CreditsEntity creditsEntity = new CreditsEntity();
//
//				creditsEntity.setUserId(creditsDto.getUserId());
//				creditsEntity.setCreditAvailable(creditsDto.getCreditAvailable() - Double.valueOf(2));
//				creditsEntity.setCreditUsed(Double.valueOf(2));
//
//				responceEntity = creditsRepository.save(creditsEntity);
//
//				creditsEntity = null;
//
//				status = true;
//			}

			findCreditesByUserId = null;
		} catch (Exception e) {
			logger.debug("Service :: useCredit :: Exception" + e.getMessage());
		}
		logger.debug("Service :: useCredit :: Exited");
		return status;
	}

}
