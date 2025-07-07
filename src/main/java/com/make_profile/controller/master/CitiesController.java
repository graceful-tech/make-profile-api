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
import org.springframework.web.bind.annotation.RestController;

import com.make_profile.dto.master.CitiesDto;
import com.make_profile.dto.master.StateDto;
import com.make_profile.service.master.CitiesService;

@RequestMapping("/cities")
@RestController
public class CitiesController {

	private static final Logger logger = LoggerFactory.getLogger(CitiesController.class);

	@Autowired
	CitiesService citiesService;

	@PostMapping("/retrive-cities")
	public ResponseEntity<?> retriveCities(@RequestBody CitiesDto citiesDto) {
		logger.debug("Controller :: retriveCities :: Entered");

		List<CitiesDto> retriveCities = citiesService.retriveCities(citiesDto);

		logger.debug("Controller :: retriveCities :: Exited");

		return new ResponseEntity<>(retriveCities, HttpStatus.OK);

	}

	@GetMapping
	public ResponseEntity<?> getAllStates() {
		logger.debug("Controller :: getAllStates :: Entered");

		List<StateDto> allStatesName = citiesService.getAllStates();

		logger.debug("Controller :: getAllStates :: Exited");

		return new ResponseEntity<>(allStatesName, HttpStatus.OK);

	}

}
