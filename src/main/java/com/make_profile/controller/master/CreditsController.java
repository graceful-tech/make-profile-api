package com.make_profile.controller.master;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.make_profile.controller.BaseController;
import com.make_profile.dto.master.CreditsDto;
import com.make_profile.service.master.CreditsService;

@RestController
@RequestMapping("/credits")
public class CreditsController extends BaseController {

	@Autowired
	CreditsService creditsService;

	private static final Logger logger = LoggerFactory.getLogger(CreditsController.class);

	@GetMapping
	public ResponseEntity<?> getcredits(@RequestParam Long userId) {
		logger.debug("Controller :: getcredits :: Entered");

		List<CreditsDto> credits = creditsService.getCredits(userId);

		logger.debug("Controller :: getcredits :: Exited");

		return new ResponseEntity<>(credits, HttpStatus.OK);

	}

	@PostMapping("/redeem")
	public ResponseEntity<?> useCredits(@RequestBody CreditsDto creditsDto) {
		logger.debug("Controller :: useCredits :: Entered");

		boolean credits = creditsService.useCredit(creditsDto);

		logger.debug("Controller :: useCredits :: Exited");

		return new ResponseEntity<>(credits, HttpStatus.OK);

	}

	@GetMapping("/get-available-credits")
	public ResponseEntity<?> getAvailableCredits(@RequestParam("templateName") String templateName,
			@RequestParam("userId") Long userId) {
		logger.debug("Controller :: getAvailableCredits :: Entered");

		Long availableCredits = creditsService.getAvailableCredits(templateName, userId);

		logger.debug("Controller :: getAvailableCredits :: Exited");

		return new ResponseEntity<>(availableCredits, HttpStatus.OK);

	}

}
