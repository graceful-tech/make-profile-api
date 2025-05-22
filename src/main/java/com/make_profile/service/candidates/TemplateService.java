package com.make_profile.service.candidates;

import com.make_profile.dto.candidates.CandidateDto;
import com.make_profile.entity.common.FieldCheckerDto;
import com.make_profile.entity.templates.UsedTemplateEntity;

public interface TemplateService {

	void saveCandidateDataInTemplate(UsedTemplateEntity usedTemplateEntity, CandidateDto candidateDto);

	FieldCheckerDto checkResumeTemplateFields(CandidateDto candidateDto);
}
