package com.make_profile.controller.master;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.make_profile.dto.WrapperDto;
import com.make_profile.dto.master.HurecomRequirementsDto;
import com.make_profile.service.master.RequirementService;

@RestController
@RequestMapping("/hurecom")
public class RequirementController {

	private static final Logger logger = LoggerFactory.getLogger(RequirementController.class);

	@Autowired
	RequirementService requirementService;

	@PostMapping("/get-requirements")
	public ResponseEntity<?> hurecomRequirement(@RequestBody HurecomRequirementsDto hurecomRequirementsDto) {
		logger.debug("Controller :: hurecomRequirement :: Entered");

		WrapperDto<HurecomRequirementsDto> hurecomRequirement = requirementService
				.hurecomRequirement(hurecomRequirementsDto);

		logger.debug("Controller :: hurecomRequirement :: Exited");
		return new ResponseEntity<>(hurecomRequirement, HttpStatus.OK);
	}

}
