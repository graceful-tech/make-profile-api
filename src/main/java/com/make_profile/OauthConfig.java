//package com.make_profile;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.make_profile.dto.user.MakeProfileUserDto;
//import com.make_profile.service.user.MakeProfileUserService;
//
//@Configuration
//public class OauthConfig {
//
//	@Autowired
//	MakeProfileUserService makeProfileUserService;
//
//	@Value("${app.frontend.url}")
//	private String url;
//
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http.authorizeHttpRequests(
//				auth -> auth.requestMatchers("/", "/login", "/oauth2/**", "/login/oauth2/**").permitAll()
//						.anyRequest().authenticated())
//				.oauth2Login(oauth2 -> oauth2.successHandler((request, response, authentication) -> {
//					OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
//					String name = oauth2User.getAttribute("name");
//					String email = oauth2User.getAttribute("email");
//					String provider = "";
//					if (authentication instanceof OAuth2AuthenticationToken) {
//						provider = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
//					}
//
//					MakeProfileUserDto createUser = makeProfileUserService.createGoogleUser(name, email, provider);
//
//					response.sendRedirect(url + "/landing");
//				}));
//
//		return http.build();
//	}
//
//}
