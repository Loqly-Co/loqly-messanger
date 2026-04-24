package com.wasd.messenger.data.response;

import java.util.Set;
import java.util.UUID;

public record LoginResponse(
	UUID userId,
	String username,
	Set<String> authorities
) {}
