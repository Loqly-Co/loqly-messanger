package com.wasd.messenger.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wasd.messenger.data.ChatMessage;
import com.wasd.messenger.data.ChatNotificationEvent;
import com.wasd.messenger.data.TypingEvent;
import com.wasd.messenger.utils.DateTimeHolder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.UUID;


@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

	private final SimpMessagingTemplate messagingTemplate;
	private final ObjectMapper   objectMapper;
	private final DateTimeHolder dateTimeHolder;

	@MessageMapping("/chat.send")
	@SneakyThrows
	public void sendMessage(@Payload ChatMessage message, Principal principal) {
		if (message.getReceiverId() == null) {
			return;
		}
		
		ChatMessage payload = new ChatMessage();
		payload.setSenderId(getUserId(principal));
		payload.setReceiverId(message.getReceiverId());
		payload.setContent(message.getContent());
		payload.setTimestamp(dateTimeHolder.userNow());
		payload.setMessageId(UUID.randomUUID());

		log.info("Sending message: {}", objectMapper.writeValueAsString(payload));
		
		messagingTemplate.convertAndSendToUser(
			payload.getReceiverId().toString(),
			"/queue/messages",
			payload
		);


		ChatNotificationEvent notification = new ChatNotificationEvent(
			getUserId(principal),
			message.getReceiverId(),
			ChatNotificationEvent.NotificationType.NEW_MESSAGE,
			payload.getContent()
		);
		
		log.info("Sent notification: {}", objectMapper.writeValueAsString(notification));
		
		messagingTemplate.convertAndSendToUser(
			payload.getReceiverId().toString(),
			"/queue/notifications",
			notification
		);
	}

	@MessageMapping("/chat.typing")
	public void typing(@Payload TypingEvent event, Principal principal) {
		if (event.receiverId() == null) {
			return;
		}
		TypingEvent correctedEvent = new TypingEvent(
			event.senderId(),
			event.receiverId(),
			event.typing()
		);

		messagingTemplate.convertAndSendToUser(
			correctedEvent.receiverId().toString(),
			"/queue/typing",
			correctedEvent
		);
	}

	@MessageMapping("/chat.markAsRead")
	@SneakyThrows
	public void markAsRead(@Payload ChatNotificationEvent event, Principal principal) {
		if (event.receiverId() == null) {
			return;
		}
		if (event.type() != ChatNotificationEvent.NotificationType.MESSAGE_READ) {
			return;
		}

		UUID reader = getUserId(principal);
		if (!reader.equals(event.senderId())) {
			return;
		}
		
		log.info("Notification event {}", objectMapper.writeValueAsString(event));

		ChatNotificationEvent readNotification = new ChatNotificationEvent(
			reader,
			event.receiverId(),
			ChatNotificationEvent.NotificationType.MESSAGE_READ,
			event.preview()
		);
		
		log.info("Read event {}", objectMapper.writeValueAsString(readNotification));

		messagingTemplate.convertAndSendToUser(
			event.receiverId().toString(), 
			"/queue/notifications",
			readNotification
		);
	}
	
	private UUID getUserId(Principal principal) {
		return UUID.fromString(principal.getName());
	}

	private String buildPreview(String content) {
		if (content == null || content.isBlank()) {
			return "";
		}

		return content.length() > 50
			   ? content.substring(0, 50) + "..."
			   : content;
	}
}