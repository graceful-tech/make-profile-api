package com.make_profile.controller.candidates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.make_profile.dto.candidates.JobScoreDto;
import com.make_profile.service.candidates.CandidateJobScoreService;

@RestController
@RequestMapping("/score-check")
public class CandidateJobScoreController {

	private static final Logger logger = LoggerFactory.getLogger(CandidateJobScoreController.class);

	@Autowired
	CandidateJobScoreService candidateJobScoreService;

	@PostMapping("/get-score")
	public ResponseEntity<?> checkCandidateJobScore(@RequestParam String jobId ,@RequestParam String candidateId ,@RequestParam String tenant) {
		logger.debug("Controller :: createCandidate :: Entered");

		JobScoreDto checkCandidateJobScore = candidateJobScoreService.checkCandidateJobScore(jobId,candidateId,tenant);

		logger.debug("Controller :: createCandidate :: Entered");

		return new ResponseEntity<>(checkCandidateJobScore, HttpStatus.OK);

	}

}
