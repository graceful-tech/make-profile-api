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

import com.make_profile.service.master.ConvertFtlToImageService;

@RestController
@RequestMapping("/images")
public class ConvertFtlToImageController {

	private static final Logger logger = LoggerFactory.getLogger(ConvertFtlToImageController.class);

	@Autowired
	ConvertFtlToImageService convertFtlToImageService;

	@GetMapping("/get-image")
	public ResponseEntity<?> convertHtmltoImage(@RequestParam String image) {
		logger.debug("Controller :: convertHtmltoImage :: Entered");

		String convertFtlToImage = convertFtlToImageService.convertFtlToImage(image);

		logger.debug("Controller :: convertHtmltoImage :: Exited");

		return new ResponseEntity<>("", HttpStatus.OK);

	}

}
