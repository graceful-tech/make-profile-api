package com.make_profile.controller.candidates;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.make_profile.dto.candidates.CandidateDto;
import com.make_profile.service.candidates.CandidateHistoryService;

@RestController
@RequestMapping("/history")
public class CandidateHistoryController {

	private static final Logger logger = LoggerFactory.getLogger(CandidateHistoryController.class);

	@Autowired
	CandidateHistoryService candidateHistoryService;

	@GetMapping("/candidate")
	private ResponseEntity<?> getCandidateHitory(@RequestHeader("username") String username,
			@RequestHeader("userId") Long userId) {
		logger.debug("Controller :: createCandidate :: Entered");

		List<CandidateDto> candidateHistory = candidateHistoryService.getCandidateHistory(username);

		logger.debug("Controller :: createCandidate :: Exited");
		return new ResponseEntity<>(candidateHistory, HttpStatus.OK);
	}

}
