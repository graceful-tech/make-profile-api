package com.make_profile.controller.candidates;

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
import com.make_profile.service.candidates.CandidateService;

@RestController
@RequestMapping("/candidate")
public class CandidatesController {

	private static final Logger logger = LoggerFactory.getLogger(CandidatesController.class);

	@Autowired
	CandidateService candidateService;

	@PostMapping("/create")
	public ResponseEntity<?> createCandidate(@RequestBody CandidateDto candidateDto) {

		logger.debug("Controller :: createCandidate :: Entered");

		CandidateDto createCandidateDto = candidateService.createCandidate(candidateDto);

		logger.debug("Controller :: createCandidate :: Exited");

		return new ResponseEntity<>(createCandidateDto, HttpStatus.OK);

	}

}
