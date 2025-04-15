package com.make_profile.controller.resume;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.make_profile.dto.candidates.CandidateDto;
import com.make_profile.service.resume.CreateResumeTemplateService;

@RestController
@RequestMapping("/resume")
public class CreateResumeTemplateController {

    private static final Logger logger = LoggerFactory.getLogger(CreateResumeTemplateController.class);

    @Autowired
    CreateResumeTemplateService createResumeTemplateService;

    @PostMapping("/create")
    public ResponseEntity<?> createResumeTemplate(@RequestBody CandidateDto candidateDto) {

        logger.debug("Controller :: createResumeTemplate :: Entered");

        createResumeTemplateService.createResumeTemplate(candidateDto);

        logger.debug("Controller :: createResumeTemplate :: Exited");

        return new ResponseEntity<>("Resume Created successfully", HttpStatus.OK);

    }
    
    @PostMapping(value = "/upload")
	public ResponseEntity<?> uploadResume(@RequestParam MultipartFile resume) {
		logger.debug("Controller :: uploadResume :: Entered");

		//boolean status = candidateResumeService.uploadResume(resume, candidateId, userName);

		logger.debug("Controller :: uploadResume :: Exited");
		return null;

//		if (!status) {
//			return new ResponseEntity<>(buildResponse(CommonConstants.HM_0048), HttpStatus.BAD_REQUEST);
//		}
		//return new ResponseEntity<>(buildResponse(CommonConstants.HM_0047), HttpStatus.OK);
	}


}
