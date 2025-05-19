package com.make_profile.service.impl.login;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.make_profile.configuration.PasswordEncryptor;
import com.make_profile.dto.login.LoginDto;
import com.make_profile.dto.user.UserDto;
import com.make_profile.entity.user.UserEntity;
import com.make_profile.repository.user.UserRepository;
import com.make_profile.security.JwtUtil;
import com.make_profile.service.login.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	PasswordEncryptor passwordEncoder;

	@Override
	public UserDto findByMobileNumber(LoginDto loginDto) {
		logger.debug("LoginServiceImpl :: findByMobileNumber :: Exited");
		UserDto userDto = null;
		UserEntity userEntity = null;
		try {
			userEntity = userRepository.findByMobileNumber(loginDto.getMobileNumber());
			if (loginDto.getMobileNumber().equals(userEntity.getMobileNumber())
					&& loginDto.getPassword().equals(passwordEncoder.decryptPassword(userEntity.getPassword()))) {
				userDto = modelMapper.map(userEntity, UserDto.class);
			}
		} catch (Exception e) {
			logger.debug("LoginServiceImpl :: userLogin :: findByMobileNumber" + e.getMessage());
		}
		logger.debug("LoginServiceImpl :: findByMobileNumber :: Exited");
		userEntity = null;
		return userDto;
	}

}
