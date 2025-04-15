package com.make_profile.service.openai;

import com.google.gson.JsonObject;
import com.make_profile.dto.requirement.fit.ResumeMatchResultDto;

public interface ExtractResultFromOpenAiService {

	ResumeMatchResultDto resultFromOpenAi(JsonObject jsonObject);

}
