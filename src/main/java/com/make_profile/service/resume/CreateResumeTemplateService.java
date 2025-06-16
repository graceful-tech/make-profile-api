package com.make_profile.service.resume;

import com.make_profile.dto.candidates.CandidateDto;
import com.make_profile.dto.master.ResponcePdfDto;
import com.make_profile.exception.MakeProfileException;

public interface CreateResumeTemplateService {

	ResponcePdfDto createResumeTemplate(CandidateDto candidateDto, String username) throws MakeProfileException;

}
