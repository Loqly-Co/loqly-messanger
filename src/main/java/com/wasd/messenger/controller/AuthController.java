package com.wasd.messenger.controller;

import com.wasd.messenger.data.request.LoginRequest;
import com.wasd.messenger.data.response.JwtTokenResponse;
import com.wasd.messenger.data.response.RestResponse;
import com.wasd.messenger.facade.JwtAuthFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final JwtAuthFacade jwtAuthFacade;

	@PostMapping("login")
	public RestResponse<JwtTokenResponse> login(@RequestBody LoginRequest request) {
		return RestResponse.success(jwtAuthFacade.getJwtToken(request));
	}
}