package com.make_profile.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	
	public ResponseEntity<?> createUser(@RequestBody MakeProfileUserDto MakeProfileUserDto){
		
		makeProfileUserService.createUser(MakeProfileUserDto);
		
		
		return null;
		
	}
	

}
