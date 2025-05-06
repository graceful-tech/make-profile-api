package com.make_profile.service.user;

import com.make_profile.dto.user.UserDto;

public interface UserService {

	boolean createUser(UserDto userDto);

//	UserDto createGoogleUser(UserDto userDto);

	UserDto createGoogleUser(String username, String email);

	UserDto getUserByUserName(String userName);

}
