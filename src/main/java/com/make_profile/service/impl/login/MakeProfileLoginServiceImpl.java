package com.make_profile.service.impl.login;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.make_profile.dto.login.MakeProfileLoginDto;
import com.make_profile.dto.user.MakeProfileUserDto;
import com.make_profile.entity.user.MakeProfileUserEntity;
import com.make_profile.repository.user.MakeProfileUserRepository;
import com.make_profile.security.JwtUtil;
import com.make_profile.service.login.MakeProfileLoginService;

@Service
public class MakeProfileLoginServiceImpl implements MakeProfileLoginService {

	private static final Logger logger = LoggerFactory.getLogger(MakeProfileLoginServiceImpl.class);

	@Autowired
	MakeProfileUserRepository makeProfileUserRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	JwtUtil jwtUtil;

	@Override
	public MakeProfileUserDto findByMobileNumber(MakeProfileLoginDto makeProfileLoginDto) {
		logger.debug("MakeProfileLoginServiceImpl :: findByMobileNumber :: Exited");
		MakeProfileUserDto userDto = null;
		MakeProfileUserEntity userEntity = null;
		try {
			userEntity = makeProfileUserRepository.findByMobileNumber(makeProfileLoginDto.getMobileNumber());
			userDto = modelMapper.map(userEntity, MakeProfileUserDto.class);
		} catch (Exception e) {
			logger.debug("MakeProfileLoginServiceImpl :: userLogin :: findByMobileNumber" + e.getMessage());
		}
		logger.debug("MakeProfileLoginServiceImpl :: findByMobileNumber :: Exited");
		userEntity = null;
		return userDto;
	}

}
