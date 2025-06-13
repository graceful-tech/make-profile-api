package com.make_profile.controller.forgotPassword;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/forgot-password")
public class ForgotPasswordController {
	
@GetMapping("/users")	
 public String forgotPassword(@RequestParam String email) {
	 
	 return null;
 }	
 @GetMapping()
 public void forgotPassword2() {
	 
 }	

 
}
