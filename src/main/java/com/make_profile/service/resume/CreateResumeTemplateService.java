package com.make_profile.service.resume;

import com.make_profile.dto.candidates.CandidateDto;
import com.make_profile.dto.master.ResponcePdfDto;

public interface CreateResumeTemplateService {

	ResponcePdfDto createResumeTemplate(CandidateDto candidateDto);

}
