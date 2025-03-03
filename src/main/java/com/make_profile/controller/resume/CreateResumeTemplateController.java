package com.make_profile.controller.resume;

import com.make_profile.dto.candidates.CandidateDto;
import com.make_profile.dto.resume.CommonResumeDto;
import com.make_profile.service.resume.CreateResumeTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/create")
public class CreateResumeTemplateController {

    private static final Logger logger = LoggerFactory.getLogger(CreateResumeTemplateController.class);

    @Autowired
    CreateResumeTemplateService createResumeTemplateService;

    @PostMapping("/resume")
    public ResponseEntity<?> createResumeTemplate(@RequestBody CandidateDto candidateDto) {

        logger.debug("Controller :: createResumeTemplate :: Entered");

        createResumeTemplateService.createResumeTemplate(candidateDto);

        logger.debug("Controller :: createResumeTemplate :: Exited");

        return new ResponseEntity<>("Resume Created successfully", HttpStatus.OK);

    }


}
