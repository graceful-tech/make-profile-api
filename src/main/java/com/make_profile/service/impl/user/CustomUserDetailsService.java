package com.make_profile.service.impl.user;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.make_profile.entity.user.UserEntity;
import com.make_profile.repository.user.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String number) throws UsernameNotFoundException {
		UserEntity user = null;
//		user = userRepository.findByMobileNumber(number);
//		if (user != null) {
//			return new User(user.getMobileNumber(), user.getPassword(),
//					Collections.singleton(new SimpleGrantedAuthority("USER_ROLE")));
//		} else {
		user = userRepository.findByUserName(number);
		if (user.getSignInAccess().equals("google")) {
			return new User(user.getName(), user.getPassword(),
					Collections.singleton(new SimpleGrantedAuthority("USER_ROLE")));
		}
		return new User(user.getMobileNumber(), user.getPassword(),
				Collections.singleton(new SimpleGrantedAuthority("USER_ROLE")));

	}

}
