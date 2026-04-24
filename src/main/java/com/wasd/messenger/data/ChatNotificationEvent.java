package com.wasd.messenger.data;

import java.util.UUID;

public record ChatNotificationEvent(
	UUID senderId,
	UUID receiverId,
	NotificationType type,
	String preview
) {

	public enum NotificationType {
		NEW_MESSAGE, USER_TYPING, MESSAGE_READ
	}
}