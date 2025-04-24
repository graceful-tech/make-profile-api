package com.make_profile.service.impl.user;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.make_profile.entity.user.MakeProfileUserEntity;
import com.make_profile.repository.user.MakeProfileUserRepository;


@Component
public class CustomUserDetailsService implements  UserDetailsService{
	
	@Autowired
	MakeProfileUserRepository makeProfileUserRepository; 

	@Override
	public UserDetails loadUserByUsername(String number) throws UsernameNotFoundException {
		MakeProfileUserEntity user = makeProfileUserRepository.findByMobileNumber(number);
		if(user==null) {
			throw new UsernameNotFoundException("user not found with mobilenumber"+user);
		}
		return new User(user.getMobileNumber(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority("USER_ROLE")));
	}

}
