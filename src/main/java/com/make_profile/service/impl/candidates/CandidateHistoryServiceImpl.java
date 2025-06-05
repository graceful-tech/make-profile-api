package com.make_profile.service.impl.candidates;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.make_profile.dto.candidates.CandidateDto;
import com.make_profile.entity.history.candidates.CandidateHistoryEntity;
import com.make_profile.repository.candidates.CandidatesRepository;
import com.make_profile.repository.history.candidates.CandidateHistoryRepository;
import com.make_profile.service.candidates.CandidateHistoryService;

@Service
public class CandidateHistoryServiceImpl implements CandidateHistoryService {

	private static final Logger logger = LoggerFactory.getLogger(CandidateHistoryServiceImpl.class);

	@Autowired
	CandidateHistoryRepository candidateHistoryRepository;

	@Autowired
	CandidatesRepository candidatesRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<CandidateDto> getCandidateHistory(String username) {
		logger.debug("Service :: getCandidateHistory :: Entered");

		Long candidateId = null;
		List<CandidateHistoryEntity> candidateHistoryList = null;
		List<CandidateDto> candidateList = new ArrayList<>();

		try {
			candidateId = candidatesRepository.getCandidateIdByUserName(username);
			candidateHistoryList = candidateHistoryRepository.getCandidateHistoryByCandidateId(candidateId);

			candidateHistoryList.forEach(history -> {
				CandidateDto candidate = new CandidateDto();
				candidate = modelMapper.map(history, CandidateDto.class);
				candidateList.add(candidate);
				candidate = null;
			});
			candidateHistoryList = null;
			candidateId = null;
		} catch (Exception e) {
			logger.debug("Service :: getCandidateHistory :: Exception" + e.getMessage());
		}
		logger.debug("Service :: getCandidateHistory :: Exited");

		return candidateList;
	}

}
