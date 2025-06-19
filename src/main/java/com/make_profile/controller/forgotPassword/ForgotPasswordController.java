package com.make_profile.controller.forgotPassword;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.make_profile.controller.BaseController;
import com.make_profile.controller.candidates.CandidateHistoryController;
import com.make_profile.dto.password.PasswordResetTokenDto;
import com.make_profile.dto.user.UserDto;
import com.make_profile.service.forgotpassword.ForgotPasswordService;
import com.make_profile.service.password.EmailService;
import com.make_profile.utility.CommonConstants;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/forgot-password")
public class ForgotPasswordController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordController.class);

	@Autowired
	ForgotPasswordService forgotPasswordService;

	@PostMapping("/users")
	public ResponseEntity<?> forgotPassword(@RequestBody PasswordResetTokenDto passwordResetTokenDto,
			HttpServletRequest request) throws Exception {
		logger.debug("Controller :: sendResetPasswordToken :: Entered");

		PasswordResetTokenDto resetTokenDto = forgotPasswordService.sendPasswordResetToken(passwordResetTokenDto,
				request);

		if (resetTokenDto != null) {
			logger.debug("Controller :: sendResetPasswordToken :: Exited");
			return new ResponseEntity<>(resetTokenDto, HttpStatus.OK);	// HM_0126
		}
		logger.debug("Controller :: verifyOtp :: Error");
		return new ResponseEntity<>(buildResponse(CommonConstants.MP_0005), HttpStatus.BAD_REQUEST);
//	HM_0125
	}

	@PostMapping("/verify-otp")
	public ResponseEntity<?> verifyOtp(@RequestBody PasswordResetTokenDto passwordResetTokenDto) {
		logger.debug("Controller :: verifyOtp :: Entered");

		boolean status = forgotPasswordService.verifyOtp(passwordResetTokenDto);
		if (!status) {
			logger.debug("Controller :: verifyOtp :: Error");
			return new ResponseEntity<>(buildResponse(CommonConstants.MP_0005), HttpStatus.BAD_REQUEST);
		}
		logger.debug("Controller :: verifyOtp :: EXited");
		return new ResponseEntity<>(buildResponse(CommonConstants.MP_0004), HttpStatus.OK);

	}

	@PutMapping("/update-password")
	public ResponseEntity<?> updateNewPassword(@RequestBody PasswordResetTokenDto passwordResetTokenDto) {
		logger.debug("Controller :: updateNewPassword :: Entered");

		boolean updatPassword = forgotPasswordService.updatPassword(passwordResetTokenDto);

		if (!updatPassword) {
			logger.debug("Controller :: updateNewPassword :: Error");
			return new ResponseEntity<>(buildResponse(CommonConstants.MP_0005), HttpStatus.BAD_REQUEST);
		}

		logger.debug("Controller :: updateNewPassword :: Exited");
		return new ResponseEntity<>(buildResponse(CommonConstants.MP_0004), HttpStatus.OK);

	}

}
