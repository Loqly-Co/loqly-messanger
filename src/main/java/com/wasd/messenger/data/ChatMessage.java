package com.wasd.messenger.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wasd.messenger.utils.DateTimeUtils;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
public class ChatMessage {
	private UUID messageId;

	private String content;

	private UUID senderId;

	private UUID receiverId;

	@JsonFormat(pattern = DateTimeUtils.DATE_TIME_PATTERN)
	private ZonedDateTime timestamp;
}