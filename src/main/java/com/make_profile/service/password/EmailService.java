package com.make_profile.service.password;

import com.make_profile.dto.EmailDto;
import com.make_profile.dto.password.PasswordResetTokenDto;
import com.make_profile.dto.user.UserDto;

import jakarta.servlet.http.HttpServletRequest;

public interface EmailService{

	boolean sendPasswordResetToken(PasswordResetTokenDto passwordResetTokenDto, HttpServletRequest request) throws Exception;
	
	boolean sendEmail(EmailDto emailDto) throws Exception;

	UserDto verifyOtp(PasswordResetTokenDto passwordResetTokenDto);
	
}