package com.make_profile.controller.resume;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.make_profile.controller.BaseController;
import com.make_profile.dto.candidates.CandidateDto;
import com.make_profile.dto.master.ResponcePdfDto;
import com.make_profile.exception.MakeProfileException;
import com.make_profile.service.resume.CreateResumeTemplateService;
import com.make_profile.utility.CommonConstants;

@RestController
@RequestMapping("/resume")
public class CreateResumeTemplateController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CreateResumeTemplateController.class);

	@Autowired
	CreateResumeTemplateService createResumeTemplateService;

	@PostMapping("/create")
	public ResponseEntity<?> createResumeTemplate(@RequestBody CandidateDto candidateDto,
			@RequestHeader("username") String username) throws MakeProfileException {

		logger.debug("Controller :: createResumeTemplate :: Entered");

		ResponcePdfDto createResumeTemplate = createResumeTemplateService.createResumeTemplate(candidateDto, username);

		logger.debug("Controller :: createResumeTemplate :: Exited");

		return new ResponseEntity<>(createResumeTemplate, HttpStatus.OK);

	}

	@PostMapping("/get-content")
	public ResponseEntity<?> getContent(@RequestBody CandidateDto candidateDto,
			@RequestHeader("username") String username) throws MakeProfileException {

		logger.debug("Controller :: createResumeTemplate :: Entered");

		CandidateDto createResumeTemplate = createResumeTemplateService.getContent(candidateDto, username);

		logger.debug("Controller :: createResumeTemplate :: Exited");

		return new ResponseEntity<>(createResumeTemplate, HttpStatus.OK);

	}

}
