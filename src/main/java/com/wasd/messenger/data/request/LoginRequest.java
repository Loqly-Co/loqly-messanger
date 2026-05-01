package com.wasd.messenger.data.request;

public record LoginRequest(
	//todo email validation
	String email,
	String password,
	String captchaToken
) {}
