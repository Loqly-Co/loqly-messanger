package com.wasd.messenger.facade;

import com.wasd.messenger.data.request.LoginRequest;
import com.wasd.messenger.data.response.JwtTokenResponse;
import com.wasd.messenger.data.response.LoginResponse;
import com.wasd.messenger.service.AuthService;
import com.wasd.messenger.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFacade {
	
	private final AuthService      authService;
	private final JwtTokenProvider jwtTokenProvider;
	
	public JwtTokenResponse getJwtToken(LoginRequest request) {

		LoginResponse usrData = authService.login(request);
		
		String token = jwtTokenProvider.createAccessToken(
			usrData.userId().toString(), 
			usrData.authorities().stream()
				.map(SimpleGrantedAuthority::new)
				.toList()
		);
		
		return new JwtTokenResponse(usrData.userId(), usrData.username(), token);
	}
}
