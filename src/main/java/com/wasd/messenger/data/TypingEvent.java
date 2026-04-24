package com.wasd.messenger.data;

import java.util.UUID;

public record TypingEvent(
	UUID senderId,
	UUID receiverId,
	boolean typing
) {}