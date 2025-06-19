package com.make_profile.service.password;

import com.make_profile.dto.EmailDto;

public interface EmailService {

	boolean sendEmail(EmailDto emailDto) throws Exception;

}