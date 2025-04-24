package com.make_profile.service.openai;

import org.springframework.web.multipart.MultipartFile;

import com.make_profile.dto.candidates.CandidateDto;

public interface ResumeDetailsAiService {

	CandidateDto getUploadResumeDetialsFromAi(MultipartFile resume);

}
