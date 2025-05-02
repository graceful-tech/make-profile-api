package com.make_profile.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.Cookie;

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

//	private final OAuth2AuthorizedClientService authorizedClientService;
//
//	public CustomOAuth2AuthenticationSuccessHandler(OAuth2AuthorizedClientService authorizedClientService) {
//		this.authorizedClientService = authorizedClientService;
//	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {

		OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;

//		OAuth2AuthorizedClient authorizedClient = authorizedClientService
//				.loadAuthorizedClient(oauth2Token.getAuthorizedClientRegistrationId(), oauth2Token.getName());

		String username = (String) oauth2Token.getPrincipal().getAttributes().get("name");

		String token = jwtUtil.generateTokenGoogle(username);

		String email = (String) oauth2Token.getPrincipal().getAttributes().get("email");

		if (email == null) {
			email = "Email not available"; 
		}

		MakeProfileUserDto GoogleUser = makeProfileUserService.createGoogleUser(username, email);
	
//		String redirectUrl = "http://localhost:4200/#/candidate?token=" + token + "&username=" + username + "&email="
//				+ email + "&id=" + String.valueOf(GoogleUser.getId());

		String redirectUri = null;
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if ("redirect_uri".equals(cookie.getName())) {
					redirectUri = java.net.URLDecoder.decode(cookie.getValue(),
							java.nio.charset.StandardCharsets.UTF_8);
					break;
				}
			}
		}

		if (redirectUri != null && !redirectUri.isEmpty()) {
			response.sendRedirect(redirectUri + "?token=" + token + "&username=" + username + "&email=" + email + "&id="
					+ String.valueOf(GoogleUser.getId()));
		} else {
			response.sendRedirect("http://localhost:4200/#/candidate");
		}

	}

}
