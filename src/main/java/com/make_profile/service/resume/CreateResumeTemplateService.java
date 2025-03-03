package com.make_profile.service.resume;

import com.make_profile.dto.candidates.CandidateDto;
import com.make_profile.dto.resume.CommonResumeDto;

public interface CreateResumeTemplateService {

    void createResumeTemplate(CandidateDto candidateDto);
}
