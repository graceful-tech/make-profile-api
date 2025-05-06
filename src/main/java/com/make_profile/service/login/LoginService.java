package com.make_profile.service.login;

import com.make_profile.dto.login.LoginDto;
import com.make_profile.dto.user.UserDto;

public interface LoginService {
//	UserDto userLogin(LoginDto loginDto);

	UserDto findByMobileNumber(LoginDto loginDto);

}
