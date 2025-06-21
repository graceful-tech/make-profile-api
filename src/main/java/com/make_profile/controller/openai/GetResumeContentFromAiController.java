package com.make_profile.controller.openai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.make_profile.dto.candidates.ResumeContentDto;
import com.make_profile.service.openai.GetResumeContentFromAiService;
import com.make_profile.service.openai.MakeProfileOpenAiService;

@RestController
@RequestMapping("/content")
public class GetResumeContentFromAiController {

	private static final Logger logger = LoggerFactory.getLogger(GetResumeContentFromAiController.class);

	@Autowired
	GetResumeContentFromAiService getResumeContentFromAiService;

	@GetMapping("/openai")
	public ResponseEntity<?> getResumeContent(@RequestParam("content") String content) {

		logger.debug("Controller :: getResumeContent :: Entered");

		ResumeContentDto resumeContent = getResumeContentFromAiService.getResumeContent(content);

		logger.debug("Controller :: getResumeContent :: Exited");
		return new ResponseEntity<>(resumeContent, HttpStatus.OK);
	}

}
