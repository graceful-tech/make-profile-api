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

import com.make_profile.controller.BaseController;
import com.make_profile.dto.master.AppliedJobDto;
import com.make_profile.service.master.AppliedJobService;
import com.make_profile.utility.CommonConstants;

@RestController
@RequestMapping("/applied-job")
public class AppliedJobController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AppliedJobController.class);

	@Autowired
	AppliedJobService appliedJobService;

	@PostMapping("/save")
	public ResponseEntity<?> saveAppplication(@RequestBody AppliedJobDto appliedJobDto) {
		logger.debug("Controller :: create :: Entered");

		boolean saveAppplication = appliedJobService.saveAppplication(appliedJobDto);

		logger.debug("Controller :: create :: Exited");
		if (saveAppplication == true) {
			return new ResponseEntity<>(buildResponse(CommonConstants.PH_0001), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(buildResponse(CommonConstants.PH_0002), HttpStatus.OK);
		}

	}

}
