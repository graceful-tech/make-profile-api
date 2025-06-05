package com.make_profile.service.candidates;

import java.util.List;

import com.make_profile.dto.candidates.CandidateDto;

public interface CandidateHistoryService {

	List<CandidateDto> getCandidateHistory(String username);
}
