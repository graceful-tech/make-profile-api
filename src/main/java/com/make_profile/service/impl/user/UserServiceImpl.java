package com.make_profile.service.impl.user;

import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.make_profile.dto.user.UserDto;
import com.make_profile.entity.user.UserEntity;
import com.make_profile.repository.user.UserRepository;
import com.make_profile.service.user.UserService;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public boolean createUser(UserDto userDto) {
		logger.debug("UserServiceImpl :: createUser :: Entered");
		boolean status = false;
		UserEntity makeProfileUserEntity = null;
		UserEntity user = null;
		try {
			user = userRepository.findByMobileNumberAndEmail(userDto.getMobileNumber(), userDto.getEmail());
			if (user == null) {
				status = true;
				if (userDto.getSignInAccess() == null) {
					userDto.setSignInAccess("LoginUser");
				}
				userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
				makeProfileUserEntity = modelMapper.map(userDto, UserEntity.class);
				userRepository.save(makeProfileUserEntity);
			}
			makeProfileUserEntity = null;
			user = null;
		} catch (Exception e) {
			status = false;
			logger.debug("UserServiceImpl :: createUser :: Error" + e.getMessage());

		}
		logger.debug("UserServiceImpl :: createUser :: Exited");

		return status;
	}

//	@Override
//	public MakeProfileUserDto createGoogleUser(MakeProfileUserDto makeProfileUserDto) {
//		logger.debug("Service :: createGoogleUser :: Entered");
//
//		MakeProfileUserDto userDto = null;
//		MakeProfileUserEntity makeProfileUserEntity = null;
//		MakeProfileUserEntity findByEmail = null;
//		MakeProfileUserEntity userEntity = new MakeProfileUserEntity();
//		try {
//			findByEmail = makeProfileUserRepository.findByEmail(makeProfileUserDto.getEmail());
//			if (Objects.isNull(findByEmail)) {
//				userEntity.setName(makeProfileUserDto.getName());
//				userEntity.setEmail(makeProfileUserDto.getEmail());
//				userEntity.setSignInAccess(makeProfileUserDto.getSignInAccess());
//				makeProfileUserEntity = makeProfileUserRepository.save(userEntity);
//				userDto = modelMapper.map(makeProfileUserEntity, MakeProfileUserDto.class);
//			} else {
//				makeProfileUserEntity = makeProfileUserRepository.findByEmail(makeProfileUserDto.getEmail());
//				userDto = modelMapper.map(makeProfileUserEntity, MakeProfileUserDto.class);
//			}
//			userEntity = null;
//			makeProfileUserEntity = null;
//			findByEmail = null;
//		} catch (Exception e) {
//			logger.debug("Service :: createGoogleUser :: Error" + e.getMessage());
//		}
//		logger.debug("Service :: createGoogleUser :: Exited");
//		return userDto;
//	}
	@Override
	public UserDto getUserByUserName(String userName) {

		UserDto makeProfileUserDto = new UserDto();

		try {
			userRepository.findByUserName(userName);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return makeProfileUserDto;

	}

	@Override
	public UserDto createGoogleUser(String userName, String email) {
		logger.debug("UserServiceImpl :: createGoogleUser :: Entered");

		UserDto userDto = null;
		UserEntity makeProfileUserEntity = null;
		UserEntity findByEmail = null;
		UserEntity userEntity = new UserEntity();
		try {
			findByEmail = userRepository.findByEmail(email);
			if (Objects.isNull(findByEmail)) {
				userEntity.setName(userName);
				userEntity.setEmail(email);
				userEntity.setSignInAccess("google");
				userEntity.setPassword((passwordEncoder.encode(userName)));
				makeProfileUserEntity = userRepository.save(userEntity);
				userDto = modelMapper.map(makeProfileUserEntity, UserDto.class);
			} else {
				makeProfileUserEntity = userRepository.findByEmail(email);
				userDto = modelMapper.map(makeProfileUserEntity, UserDto.class);
			}
			userEntity = null;
			makeProfileUserEntity = null;
			findByEmail = null;
		} catch (Exception e) {
			logger.debug("UserServiceImpl :: createGoogleUser :: Error" + e.getMessage());
		}
		logger.debug("UserServiceImpl :: createGoogleUser :: Exited");
		return userDto;
	}

}
