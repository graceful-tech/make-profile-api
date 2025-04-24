package com.make_profile.service.openai;

import com.google.gson.JsonObject;
import com.make_profile.dto.candidates.CandidateDto;

public interface ResumeJsonIntoStringService {

	CandidateDto resumeJsonToString(JsonObject jsonObject);
}
