package com.make_profile.service.impl.user;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import com.make_profile.dto.user.MakeProfileUserDto;
import com.make_profile.entity.user.MakeProfileUserEntity;
import com.make_profile.repository.user.MakeProfileUserRepository;
import com.make_profile.service.user.MakeProfileUserService;

@Service
public class MakeProfileUserServiceImpl implements MakeProfileUserService {

	private static final Logger logger = LoggerFactory.getLogger(MakeProfileUserServiceImpl.class);

	@Autowired
	private MakeProfileUserRepository makeProfileUserRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public boolean createUser(MakeProfileUserDto makeProfileUserDto) {
		logger.debug("Service :: createUser :: Entered");
		boolean status = false;
		MakeProfileUserEntity makeProfileUserEntity = null;
		MakeProfileUserEntity user = null;
		try {
			user = makeProfileUserRepository.findByMobileNumberAndEmail(makeProfileUserDto.getMobileNumber(),
					makeProfileUserDto.getEmail());
			if (user == null) {
				status = true;
				if (makeProfileUserDto.getSignInAccess() == null) {
					makeProfileUserDto.setSignInAccess("LoginUser");
				}
				makeProfileUserDto.setPassword(passwordEncoder.encode(makeProfileUserDto.getPassword()));
				makeProfileUserEntity = modelMapper.map(makeProfileUserDto, MakeProfileUserEntity.class);
				makeProfileUserRepository.save(makeProfileUserEntity);
			}
			makeProfileUserEntity = null;
			user = null;
		} catch (Exception e) {
			logger.debug("Service :: createUser :: Error" + e.getMessage());
		}
		logger.debug("Service :: createUser :: Exited");

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
	public MakeProfileUserDto createGoogleUser(String userName,String email) {
		logger.debug("Service :: createGoogleUser :: Entered");

		MakeProfileUserDto userDto = null;
		MakeProfileUserEntity makeProfileUserEntity = null;
		MakeProfileUserEntity findByEmail = null;
		MakeProfileUserEntity userEntity = new MakeProfileUserEntity();
		try {
			findByEmail = makeProfileUserRepository.findByEmail(email);
			if (Objects.isNull(findByEmail)) {
				userEntity.setName(userName);
				userEntity.setEmail(email);
				userEntity.setSignInAccess("google");
				userEntity.setPassword((passwordEncoder.encode(userName)));
				makeProfileUserEntity = makeProfileUserRepository.save(userEntity);
				userDto = modelMapper.map(makeProfileUserEntity, MakeProfileUserDto.class);
			} else {
				makeProfileUserEntity = makeProfileUserRepository.findByEmail(email);
				userDto = modelMapper.map(makeProfileUserEntity, MakeProfileUserDto.class);
			}
			userEntity = null;
			makeProfileUserEntity = null;
			findByEmail = null;
		} catch (Exception e) {
			logger.debug("Service :: createGoogleUser :: Error" + e.getMessage());
		}
		logger.debug("Service :: createGoogleUser :: Exited");
		return userDto;
	}

}
