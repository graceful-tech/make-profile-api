package com.make_profile.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.make_profile.dto.user.MakeProfileUserDto;
import com.make_profile.security.JwtUtil;
import com.make_profile.service.user.MakeProfileUserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomOAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	@Lazy
	MakeProfileUserService makeProfileUserService;

	private final OAuth2AuthorizedClientService authorizedClientService;

	public CustomOAuth2AuthenticationSuccessHandler(OAuth2AuthorizedClientService authorizedClientService) {
		this.authorizedClientService = authorizedClientService;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
		// Get the OAuth2AuthenticationToken from the authentication object
		OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;

		// Retrieve the OAuth2AuthorizedClient
		OAuth2AuthorizedClient authorizedClient = authorizedClientService
				.loadAuthorizedClient(oauth2Token.getAuthorizedClientRegistrationId(), oauth2Token.getName());

		String username = (String) oauth2Token.getPrincipal().getAttributes().get("name");

		String token = jwtUtil.generateTokenGoogle(username);

		String email = (String) oauth2Token.getPrincipal().getAttributes().get("email");

		// Check if email is not null (OAuth2 provider like Google usually provides
		// email)
		if (email == null) {
			email = "Email not available"; // or you can handle this differently
		}

		MakeProfileUserDto GoogleUser = makeProfileUserService.createGoogleUser(username, email);
       //TODO
	
		String redirectUrl = "http://localhost:4200/#/candidate?token=" + token + "&username=" + username + "&email="
				+ email + "&id=" + String.valueOf(GoogleUser.getId());

		response.sendRedirect(redirectUrl);
	}

}
