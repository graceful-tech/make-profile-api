package com.make_profile.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.make_profile.controller.BaseController;
import com.make_profile.dto.user.UserDto;
import com.make_profile.service.user.UserService;
import com.make_profile.utility.CommonConstants;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	@PostMapping("/create")
	public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
		logger.debug("UserController :: createUser :: Entered");
		boolean user = false;
		user = userService.createUser(userDto);

		logger.debug("UserController :: createUser :: Exited");
		if (!user) {
			logger.debug("UserController :: createUser :: Error");
			return new ResponseEntity<>(buildResponse(CommonConstants.PM_0003), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(buildResponse(CommonConstants.PM_0004), HttpStatus.OK);
	}

	@GetMapping("/get_user/{userName}")
	public ResponseEntity<?> getUserByUserName(@PathVariable String userName) {
		logger.debug("UserController :: getUserByUserName :: Entered");

		UserDto user = userService.getUserByUserName(userName);

		logger.debug("UserController :: getUserByUserName :: Exited");
		return new ResponseEntity<>(user, HttpStatus.OK);

	}
	@PutMapping("/update_user/{userName}")
	public ResponseEntity<?> updateUser(@RequestBody UserDto userDto, @RequestHeader("username") String userName)
			throws Exception {
		boolean status=false;
		logger.debug("Controller :: updateUser :: Entered");
		userDto.setUserName(userName);
		status = userService.updateUser(userDto, userName);

		logger.debug("Controller :: updateUser :: Exited");
		
		if (!status) {
			logger.debug("Controller :: updateUser :: Error");
			return new ResponseEntity<>(buildResponse(CommonConstants.PM_0003), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(buildResponse(CommonConstants.PM_0004), HttpStatus.OK);
	

		
	
}
}
