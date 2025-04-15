package com.make_profile.controller.master;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.make_profile.dto.master.CreditsDto;
import com.make_profile.service.master.CreditsService;

@RestController
@RequestMapping("/credits")
public class CreditsController {

	@Autowired
	CreditsService creditsService;

	private static final Logger logger = LoggerFactory.getLogger(CreditsController.class);

	@GetMapping
	public ResponseEntity<?> getcredits(@RequestParam Long candidateId) {
		logger.debug("Controller :: getcredits :: Entered");

		CreditsDto credits = creditsService.getCredits(candidateId);

		logger.debug("Controller :: getcredits :: Exited");

		return new ResponseEntity<>(credits, HttpStatus.OK);

	}

}
