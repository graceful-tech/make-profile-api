package com.make_profile.service.login;

import com.make_profile.dto.login.MakeProfileLoginDto;
import com.make_profile.dto.user.MakeProfileUserDto;

public interface MakeProfileLoginService {

//	MakeProfileUserDto userLogin(MakeProfileLoginDto makeProfileLoginDto);

	MakeProfileUserDto findByMobileNumber(MakeProfileLoginDto makeProfileLoginDto);

}
