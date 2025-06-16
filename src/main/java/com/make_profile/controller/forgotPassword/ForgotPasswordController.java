package com.make_profile.controller.forgotPassword;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.make_profile.controller.BaseController;
import com.make_profile.controller.candidates.CandidateHistoryController;
import com.make_profile.dto.password.PasswordResetTokenDto;
import com.make_profile.dto.user.UserDto;
import com.make_profile.service.password.EmailService;
import com.make_profile.utility.CommonConstants;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/forgot-password")
public class ForgotPasswordController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CandidateHistoryController.class);

	@Autowired
	EmailService emailService;

	@PostMapping("/users")
	public ResponseEntity<?> forgotPassword(@RequestBody PasswordResetTokenDto passwordResetTokenDto,
			HttpServletRequest request) throws Exception {
		logger.debug("Controller :: sendResetPasswordToken :: Entered");

		boolean status = emailService.sendPasswordResetToken(passwordResetTokenDto, request);

		logger.debug("Controller :: sendResetPasswordToken :: Exited");

		if (!status) {
			return new ResponseEntity<>(buildResponse(CommonConstants.MP_0004), HttpStatus.BAD_REQUEST);
//		HM_0126
		}
		return new ResponseEntity<>(buildResponse(CommonConstants.MP_0004), HttpStatus.OK);
//	HM_0125
	}

	@PostMapping("/verify-otp")
	public ResponseEntity<?> verifyOtp(@RequestBody PasswordResetTokenDto passwordResetTokenDto) {
		logger.debug("Controller :: sendResetPasswordToken :: Entered");

		UserDto userDto = emailService.verifyOtp(passwordResetTokenDto);
		if (userDto != null) {
			logger.debug("Controller :: sendResetPasswordToken :: Entered");
			return ResponseEntity.ok(userDto);
		}
		logger.debug("Controller :: sendResetPasswordToken :: Error");
		return new ResponseEntity<>(buildResponse(CommonConstants.MP_0004), HttpStatus.OK);

	}
	
	@PostMapping("/")
	public ResponseEntity<?> updateNewPassword(@RequestBody PasswordResetTokenDto passwordResetTokenDto){
		return null;
		
	}

}
