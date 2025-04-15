package com.make_profile.service.candidates;

import com.make_profile.dto.candidates.CandidateDto;
import com.make_profile.dto.candidates.CandidateImageDto;

public interface CandidateService {

	CandidateDto createCandidate(CandidateDto candidateDto);

	CandidateDto getCandidateById(Long id);

	byte[] uploadCandidateImage(CandidateImageDto candidateImageDto);

}
