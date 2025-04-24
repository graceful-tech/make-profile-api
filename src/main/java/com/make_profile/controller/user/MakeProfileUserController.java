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

import com.make_profile.utility.CommonConstants;
import com.make_profile.controller.BaseController;
import com.make_profile.dto.user.MakeProfileUserDto;
import com.make_profile.service.user.MakeProfileUserService;

@RestController
@RequestMapping("/user")
public class MakeProfileUserController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(MakeProfileUserController.class);

	@Autowired
	private MakeProfileUserService makeProfileUserService;

	@PostMapping("/create")
	public ResponseEntity<?> createUser(@RequestBody MakeProfileUserDto makeProfileUserDto) {
		logger.debug("Service :: createUser :: Entered");
		boolean userDto = makeProfileUserService.createUser(makeProfileUserDto);

		logger.debug("Service :: createUser :: Exited");
		if (!userDto) {
			return new ResponseEntity<>(buildResponse(CommonConstants.PM_0003), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(buildResponse(CommonConstants.PM_0004), HttpStatus.OK);
	}

}
