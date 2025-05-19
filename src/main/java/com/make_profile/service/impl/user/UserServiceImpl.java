package com.make_profile.service.impl.user;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.make_profile.configuration.PasswordEncryptor;
import com.make_profile.controller.login.LoginController;
import com.make_profile.dto.user.UserDto;
import com.make_profile.entity.user.UserEntity;
import com.make_profile.repository.user.UserRepository;
import com.make_profile.service.user.UserService;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncryptor passwordEncoder;

	@Autowired
	LoginController loginController;

	@Override
	public boolean createUser(UserDto userDto) {
		logger.debug("UserServiceImpl :: createUser :: Entered");
		boolean status = false;
		UserEntity makeProfileUserEntity = null;
		try {
//			userRepository.findByMobileNumberAndEmail(userDto.getMobileNumber(), userDto.getEmail());
			if (userRepository.findByMobileNumberAndEmail(userDto.getMobileNumber(), userDto.getEmail()) == 0) {
				status = true;
				if (userDto.getSignInAccess() == null) {
					userDto.setSignInAccess("LoginUser");
				}
				userDto.setPassword(passwordEncoder.encryptPassword(userDto.getPassword()));
				makeProfileUserEntity = convertUserDtoToUserEntity(userDto);
				userRepository.save(makeProfileUserEntity);
			}
			makeProfileUserEntity = null;
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

		try {

			UserEntity userEntity = userRepository.findByUserName(userName);
			String decrypt = passwordEncoder.decryptPassword(userEntity.getPassword());
			userEntity.setPassword(decrypt);

			return convertUserEntityToUserDto(userEntity);

		} catch (Exception e) {
			return null;
		}
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
				userEntity.setPassword(passwordEncoder.encryptPassword(userName));
				userEntity.setUserName(userName);
				makeProfileUserEntity = userRepository.save(userEntity);
				userDto = convertUserEntityToUserDto(makeProfileUserEntity);
			} else {
				makeProfileUserEntity = userRepository.findByEmail(email);
				userDto = convertUserEntityToUserDto(makeProfileUserEntity);
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

	public UserDto convertUserEntityToUserDto(UserEntity userEntity) {
		UserDto userDetails = new UserDto();
		userDetails.setEmail(userEntity.getEmail());
		userDetails.setName(userEntity.getName());
		userDetails.setMobileNumber(userEntity.getMobileNumber());
		userDetails.setId(userEntity.getId());
		userDetails.setUserName(userEntity.getUserName());
		userDetails.setPassword(userEntity.getPassword());
		userDetails.setSignInAccess(userEntity.getSignInAccess());
		return userDetails;
	}

	public UserEntity convertUserDtoToUserEntity(UserDto userDto) {
		UserEntity userDetails = new UserEntity();
		userDetails.setEmail(userDto.getEmail());
		userDetails.setName(userDto.getName());
		userDetails.setMobileNumber(userDto.getMobileNumber());
		userDetails.setId(userDto.getId());
		userDetails.setUserName(userDto.getUserName());
		userDetails.setPassword(userDto.getPassword());
		userDetails.setSignInAccess(userDto.getSignInAccess());
		return userDetails;
	}

	@Override
	public boolean updateUser(UserDto userDto, String userName) {
		boolean status = false;
		UserEntity userEntity = null;
		try {

			status = true;
			userEntity = convertUserDtoToUserEntity(userDto);
			userEntity.setPassword(passwordEncoder.encryptPassword(userEntity.getPassword()));

			userRepository.save(userEntity);
//				String refreshToken = loginController.refreshToken(userDto);

			userEntity = null;
			return status;
		} catch (Exception e) {

			return status;
		}

	}

}
