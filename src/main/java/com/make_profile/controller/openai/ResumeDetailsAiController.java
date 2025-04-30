package com.make_profile.controller.openai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.make_profile.dto.candidates.CandidateDto;
import com.make_profile.dto.candidates.CandidateImageDto;
import com.make_profile.service.openai.MakeProfileOpenAiService;
import com.make_profile.service.openai.ResumeDetailsAiService;

@RestController
@RequestMapping("/resume-ai")
public class ResumeDetailsAiController {

	private static final Logger logger = LoggerFactory.getLogger(ResumeDetailsAiController.class);

	@Autowired
	ResumeDetailsAiService resumeDetailsAiService;

	@PostMapping("/upload")
	public ResponseEntity<?> getResumeDetailsFromOpenAi(@RequestParam("resume") MultipartFile resume,
			@RequestParam("userName") String userName, @RequestHeader("userId") String userId) {

		logger.debug("Controller :: getResumeDetailsFromOpenAi :: Entered");

		CandidateDto uploadResumeDetialsFromAi = resumeDetailsAiService.getUploadResumeDetialsFromAi(resume, userName);

		logger.debug("Controller :: getResumeDetailsFromOpenAi :: Exited");

		return new ResponseEntity<>(uploadResumeDetialsFromAi, HttpStatus.OK);

	}
}
