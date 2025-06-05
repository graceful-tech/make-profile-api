package com.make_profile.controller.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.make_profile.dto.login.LoginDto;
import com.make_profile.dto.user.UserDto;
import com.make_profile.security.JwtUtil;
import com.make_profile.service.login.LoginService;
import com.make_profile.utility.CommonConstants;

@RestController
@RequestMapping("/auth")
public class LoginController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	AuthenticationManager authManager;

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	LoginService loginService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
		logger.debug("LoginController :: login :: Entered");
		UserDto userDto = null;
		try {
			userDto = loginService.findByMobileNumber(loginDto);
			String token = jwtUtil.generateToken(userDto.getUserName());
			userDto.setToken(token);

			logger.debug("LoginController :: userLogin :: Exited");
			return ResponseEntity.ok(userDto);
		} catch (Exception e) {
			logger.debug("LoginController :: userLogin :: Error");
		}
		return new ResponseEntity<>(buildResponse(CommonConstants.MP_0005), HttpStatus.BAD_REQUEST);
	}

//	public String refreshToken(UserDto loginDto) {
//		logger.debug("LoginController :: login :: Entered");
//		UserDto userDto = null;
//		try {
//			Authentication authentication = authManager.authenticate(
//					new UsernamePasswordAuthenticationToken(loginDto.getMobileNumber(), loginDto.getPassword()));
//			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//			String token = jwtUtil.generateToken(userDetails);
//			logger.debug("LoginController :: userLogin :: Exited");
//			return token;
//
//		} catch (Exception e) {
//
//			logger.debug("LoginController :: userLogin :: Error");
//		}
//		return null;
//	}

//	@PostMapping("/google-login")
//	public ResponseEntity<?> googleuserCreate(@RequestBody MakeProfileUserDto makeProfileUserDto) {
//		logger.debug("LoginController :: googleuserCreate :: Entered");
//		try {
//			MakeProfileUserDto createGoogleUser = makeProfileUserService.createGoogleUser(makeProfileUserDto);
//			logger.debug("LoginController :: googleuserCreate :: Exited");
//			return new ResponseEntity<>(createGoogleUser, HttpStatus.OK);
//		} catch (Exception e) {
//			logger.debug("LoginController :: googleuserCreate :: Error");
//		}
//		return new ResponseEntity<>("Login Failed", HttpStatus.NOT_FOUND);
//	}

}
