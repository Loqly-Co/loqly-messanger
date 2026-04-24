package com.wasd.messenger.data.response;

import java.util.UUID;

public record JwtTokenResponse(
	UUID userId,
	String username,
	String token
) {}
