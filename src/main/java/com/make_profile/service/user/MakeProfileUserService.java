package com.make_profile.service.user;

import com.make_profile.dto.user.MakeProfileUserDto;

public interface MakeProfileUserService {

	boolean createUser(MakeProfileUserDto makeProfileUserDto);

//	MakeProfileUserDto createGoogleUser(MakeProfileUserDto makeProfileUserDto);
	
	MakeProfileUserDto createGoogleUser(String username,String email);

	

}
