package com.wasd.messenger.data.request;

import com.wasd.messenger.data.ChatMessage;

public record CreateChatRequest(
	ChatMessage message
) {}
