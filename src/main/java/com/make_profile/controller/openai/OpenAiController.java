package com.make_profile.controller.openai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.make_profile.service.openai.MakeProfileOpenAiService;

@RestController
@RequestMapping("/open-ai")
public class OpenAiController {

	private static final Logger logger = LoggerFactory.getLogger(OpenAiController.class);

	@Autowired
	MakeProfileOpenAiService makeProfileOpenAiService;

	@PostMapping("/openai")
	public void makeProfileOpenAi(@RequestParam("content") String content) {

		logger.debug("Controller :: aiController :: Entered");

		makeProfileOpenAiService.makeProfileAi(content);

		logger.debug("Controller :: aiController :: Exited");

	}

}
