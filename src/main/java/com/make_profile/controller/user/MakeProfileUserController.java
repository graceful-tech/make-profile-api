package com.make_profile.controller.user;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.make_profile.dto.user.MakeProfileUserDto;
import com.make_profile.service.user.MakeProfileUserService;

@RestController
@RequestMapping("/user")
public class MakeProfileUserController {

	@Autowired
	private MakeProfileUserService makeProfileUserService;

	
	@RequestMapping("/create")
	public ResponseEntity<?> createUser(@RequestBody MakeProfileUserDto makeProfileUserDto) {

		try {

			MakeProfileUserDto userDto = makeProfileUserService.createUser(makeProfileUserDto);
			return ResponseEntity.ok(userDto);
		}

		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body("User creation failed");
		}

	}

}
