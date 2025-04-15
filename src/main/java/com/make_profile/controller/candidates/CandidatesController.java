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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.make_profile.dto.candidates.CandidateDto;
import com.make_profile.dto.candidates.CandidateImageDto;
import com.make_profile.service.candidates.CandidateService;

import jakarta.websocket.server.PathParam;

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

	@GetMapping("/{id}")
	public ResponseEntity<?> getCandidateById(@PathParam("id") Long id) {

		logger.debug("Controller :: getCandidateById :: Entered");

		CandidateDto candidateById = candidateService.getCandidateById(id);

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

}
