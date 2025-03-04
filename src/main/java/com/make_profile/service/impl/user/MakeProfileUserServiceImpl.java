package com.make_profile.service.impl.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.make_profile.dto.user.MakeProfileUserDto;
import com.make_profile.entity.user.MakeProfileUserEntity;
import com.make_profile.repository.user.MakeProfileUserRepository;
import com.make_profile.service.user.MakeProfileUserService;

@Service
public class MakeProfileUserServiceImpl implements MakeProfileUserService {

	@Autowired
	private MakeProfileUserRepository makeProfileUserRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public MakeProfileUserDto createUser(MakeProfileUserDto makeProfileUserDto) {

		MakeProfileUserEntity makeProfileUserEntity = modelMapper.map(makeProfileUserDto, MakeProfileUserEntity.class);
		MakeProfileUserEntity save = makeProfileUserRepository.save(makeProfileUserEntity);
		MakeProfileUserDto user = modelMapper.map(save, MakeProfileUserDto.class);
		return user;
	}

}
