package com.make_profile.controller.candidates;

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

import com.make_profile.dto.candidates.CandidateDto;
import com.make_profile.dto.candidates.CandidateImageDto;
import com.make_profile.service.candidates.CandidateService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/candidate")
public class CandidatesController {

	private static final Logger logger = LoggerFactory.getLogger(CandidatesController.class);

	@Autowired
	CandidateService candidateService;

	@Autowired
	HttpServletRequest httpRequest;

	@PostMapping("/create")
	public ResponseEntity<?> createCandidate(@RequestBody CandidateDto candidateDto,
			@RequestHeader("username") String username, @RequestHeader("userid") Long userid) {
		logger.debug("Controller :: createCandidate :: Entered");

//		String userName = httpRequest.getHeader("username");
//		String userId = httpRequest.getHeader("userid");
//
//		if (userName != null) {
//			candidateDto.setCreatedUserName(userName);
//		}
		if (userid != null) {
			candidateDto.setCreatedUser(Long.valueOf(userid));
		}
		CandidateDto createCandidateDto = candidateService.createCandidate(candidateDto, username);

//		userName = null;
//		userId = null;

		logger.debug("Controller :: createCandidate :: Exited");

		return new ResponseEntity<>(createCandidateDto, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<?> getCandidateById() {
		logger.debug("Controller :: getCandidateById :: Entered");

		String userName = httpRequest.getHeader("userName");
		CandidateDto candidateById = null;

		if (userName != null) {
			candidateById = candidateService.getCandidateById(userName);
		}
		userName = null;
		logger.debug("Controller :: getCandidateById :: Exited");

		return new ResponseEntity<>(candidateById, HttpStatus.OK);
	}

	@PostMapping("/upload-image")
	public ResponseEntity<?> uploadCandidateImage(@ModelAttribute CandidateImageDto candidateImageDto) {
		logger.debug("Controller :: uploadCandidateImage :: Entered");

		byte[] uploadCandidateImagId = candidateService.uploadCandidateImage(candidateImageDto);

		logger.debug("Controller :: uploadCandidateImage :: Exited");

		return new ResponseEntity<>(uploadCandidateImagId, HttpStatus.OK);
	}

	@PostMapping("/get-image")
	public ResponseEntity<?> getCandidateImage(@RequestParam("candidateId") Long candidateId) {
		logger.debug("Controller :: uploadCandidateImage :: Entered");

		byte[] uploadCandidateImagId = candidateService.getCandidateImage(candidateId);

		logger.debug("Controller :: uploadCandidateImage :: Exited");

		return new ResponseEntity<>(uploadCandidateImagId, HttpStatus.OK);
	}

}
