package com.make_profile.service.openai;

import com.make_profile.dto.candidates.CandidateDto;

public interface MakeProfileOpenAiService {

	void makeProfileAi(String content);

	CandidateDto getSummaryFromAi(String Content);

}
