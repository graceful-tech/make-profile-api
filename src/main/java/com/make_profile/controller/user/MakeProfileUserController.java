package com.make_profile.controller.user;

import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.make_profile.utility.CommonConstants;
import com.make_profile.controller.BaseController;
import com.make_profile.dto.user.MakeProfileUserDto;
import com.make_profile.service.user.MakeProfileUserService;

@RestController
@RequestMapping("/user")
public class MakeProfileUserController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(MakeProfileUserController.class);

	@Autowired
	MakeProfileUserService makeProfileUserService;

	@PostMapping("/create")
	public ResponseEntity<?> createUser(@RequestBody MakeProfileUserDto makeProfileUserDto) {
		logger.debug("Controller :: createUser :: Entered");
		boolean userDto = false;
		userDto = makeProfileUserService.createUser(makeProfileUserDto);

		logger.debug("Controller :: createUser :: Exited");
		if (!userDto) {
			logger.debug("Controller :: createUser :: Error");
			return new ResponseEntity<>(buildResponse(CommonConstants.PM_0003), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(buildResponse(CommonConstants.PM_0004), HttpStatus.OK);
	}

	@GetMapping("/get_user/{userName}")
	public ResponseEntity<?> getUserByUserName(@PathVariable String userName) {
		logger.debug("Controller :: getUserByUserName :: Entered");

		MakeProfileUserDto user = makeProfileUserService.getUserByUserName(userName);

		logger.debug("Controller :: getUserByUserName :: Exited");
		return new ResponseEntity<>(user, HttpStatus.OK);

	}
}
