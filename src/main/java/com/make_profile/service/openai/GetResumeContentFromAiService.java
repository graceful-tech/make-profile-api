package com.make_profile.service.openai;

import com.make_profile.dto.candidates.ResumeContentDto;

public interface GetResumeContentFromAiService {

	ResumeContentDto getResumeContent(String name);

}
