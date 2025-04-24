package com.make_profile.service.impl.master;

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
	public CreditsDto getCredits(Long candidateId) {
		logger.debug("Service :: getCredits :: Entered");

		CreditsDto creditsDto = null;
		try {
			CreditsEntity creditsEntity = creditsRepository.findById(candidateId).get();

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
	public CreditsDto addCredits(CreditsDto creditsDto) {

		logger.debug("Service :: addCredits :: Entered");

		CreditsDto credits = null;
		CreditsEntity findByCandidate_Id = null;
		CreditsEntity responceEntity = null;
		try {

			findByCandidate_Id = creditsRepository.findCreditsByCandidateId(creditsDto.getCandidateId());

			if (Objects.nonNull(findByCandidate_Id)) {
				findByCandidate_Id.setCandidateId(creditsDto.getCandidateId());
				findByCandidate_Id.setCreditAvailable(
						findByCandidate_Id.getCreditAvailable() + Double.valueOf(creditsDto.getCreditAvailable()));
				findByCandidate_Id.setId(findByCandidate_Id.getId());

				responceEntity = creditsRepository.save(findByCandidate_Id);

				findByCandidate_Id = null;
			}

			else {
				CreditsEntity candidateEntity = new CreditsEntity();

				candidateEntity.setCandidateId(creditsDto.getCandidateId());
				candidateEntity.setCreditAvailable(Double.valueOf(creditsDto.getCreditAvailable()));
				responceEntity = creditsRepository.save(candidateEntity);

				candidateEntity = null;
			}

			credits.setCandidateId(responceEntity.getCandidateId());
			credits.setCreditAvailable(creditsDto.getCreditAvailable());

			responceEntity = null;

		} catch (Exception e) {
			logger.debug("Service :: addCredits :: Exception" + e.getMessage());
		}
		logger.debug("Service :: addCredits :: Exited");
		return credits;

	}

	// TODO candidate comes from login
	@Override
	public CreditsDto useCredit(CreditsDto creditsDto) {
		logger.debug("Service :: useCredit :: Entered");

		CreditsDto credits = null;
		CreditsEntity findByCandidate_Id = null;
		CreditsEntity responceEntity = null;
		try {

			findByCandidate_Id = creditsRepository.findCreditsByCandidateId(creditsDto.getCandidateId());

			if (Objects.nonNull(findByCandidate_Id)) {
				if (findByCandidate_Id.getCreditAvailable() > 2.0) {

					findByCandidate_Id.setCreditAvailable(findByCandidate_Id.getCreditAvailable() - Double.valueOf(2));
					findByCandidate_Id.setCandidateId(findByCandidate_Id.getCandidateId());
					findByCandidate_Id.setCreditUsed(findByCandidate_Id.getCreditUsed() + Double.valueOf(2));
					findByCandidate_Id.setId(findByCandidate_Id.getId());

					responceEntity = creditsRepository.save(findByCandidate_Id);
				}
			} else {
				CreditsEntity creditsEntity = new CreditsEntity();

				creditsEntity.setCreditAvailable(creditsDto.getCreditAvailable() - Double.valueOf(2));
				creditsEntity.setCandidateId(creditsDto.getCandidateId());
				creditsEntity.setCreditUsed(Double.valueOf(2));

				responceEntity = creditsRepository.save(creditsEntity);

				creditsEntity = null;
			}
			credits.setCandidateId(responceEntity.getCandidateId());
			credits.setCreditAvailable(responceEntity.getCreditAvailable());

			responceEntity = null;
		} catch (Exception e) {
			logger.debug("Service :: useCredit :: Exception" + e.getMessage());
		}
		logger.debug("Service :: useCredit :: Exited");
		return credits;

	}

}
