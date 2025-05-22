package com.make_profile.controller.candidates;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.make_profile.dto.candidates.CandidateDto;
import com.make_profile.entity.common.FieldCheckerDto;
import com.make_profile.service.candidates.TemplateService;

import io.jsonwebtoken.lang.Arrays;

@RequestMapping("/template")
@RestController
public class TemplatesController {

	private static final Logger logger = LoggerFactory.getLogger(TemplatesController.class);

	@Autowired
	TemplateService templateService;

	@PostMapping("/checker")
	public ResponseEntity<?> checkResumeTemplateField(@RequestBody CandidateDto candidateDto) {
		logger.debug("Controller :: checkResumeTemplate :: Entered");

		FieldCheckerDto missingFields = templateService.checkResumeTemplateFields(candidateDto);

		logger.debug("Controller :: checkResumeTemplate :: Exited");

		return new ResponseEntity<>(missingFields, HttpStatus.OK);
	}

}
