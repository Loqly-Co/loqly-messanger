package com.wasd.messenger.data;

public record ChatShutdownMessage(
	String message,
	int reconnectInSeconds
) {}