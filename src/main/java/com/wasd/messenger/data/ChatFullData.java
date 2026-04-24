package com.wasd.messenger.data;

import java.util.List;
import java.util.UUID;

public record ChatFullData(
	UUID chatId,
	String userPublicKey,
	List<ChatMessage> messages
) {

}
