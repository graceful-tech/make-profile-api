package com.make_profile.controller.login;

import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.make_profile.controller.BaseController;
import com.make_profile.dto.login.MakeProfileLoginDto;
import com.make_profile.dto.user.MakeProfileUserDto;
import com.make_profile.security.JwtUtil;
import com.make_profile.service.login.MakeProfileLoginService;
import com.make_profile.service.user.MakeProfileUserService;
import com.make_profile.utility.CommonConstants;

@RestController
@RequestMapping("/auth")
public class MakeProfileLoginController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(MakeProfileLoginController.class);

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private MakeProfileUserService makeProfileUserService;

	@Autowired
	MakeProfileLoginService makeProfileLoginService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody MakeProfileLoginDto makeProfileLoginDto) {
		logger.debug("MakeProfileLoginController :: login :: Entered");
		MakeProfileUserDto userDto = null;
		try {
			Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(
					makeProfileLoginDto.getMobileNumber(), makeProfileLoginDto.getPassword()));
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String token = jwtUtil.generateToken(userDetails);
			userDto = makeProfileLoginService.findByMobileNumber(makeProfileLoginDto);
			userDto.setToken(token);

			logger.debug("MakeProfileLoginController :: userLogin :: Exited");
			return ResponseEntity.ok(userDto);
		} catch (Exception e) {
			logger.debug("MakeProfileLoginController :: userLogin :: Error");
		}
		return new ResponseEntity<>(buildResponse(CommonConstants.PM_0005), HttpStatus.BAD_REQUEST);
	}

//	@PostMapping("/google-login")
//	public ResponseEntity<?> googleuserCreate(@RequestBody MakeProfileUserDto makeProfileUserDto) {
//		logger.debug("MakeProfileLoginController :: googleuserCreate :: Entered");
//		try {
//			MakeProfileUserDto createGoogleUser = makeProfileUserService.createGoogleUser(makeProfileUserDto);
//			logger.debug("MakeProfileLoginController :: googleuserCreate :: Exited");
//			return new ResponseEntity<>(createGoogleUser, HttpStatus.OK);
//		} catch (Exception e) {
//			logger.debug("MakeProfileLoginController :: googleuserCreate :: Error");
//		}
//		return new ResponseEntity<>("Login Failed", HttpStatus.NOT_FOUND);
//	}

}
