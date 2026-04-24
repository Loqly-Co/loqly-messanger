package com.wasd.messenger.app;

import com.wasd.messenger.data.ChatShutdownMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketGracefulShutdownHandler {

	private final SimpMessagingTemplate messagingTemplate;

	@Getter
	private volatile AtomicBoolean shuttingDown = new AtomicBoolean(false);

	@EventListener(ContextClosedEvent.class)
	public void onApplicationShutdown(ContextClosedEvent event) {
		shuttingDown = new AtomicBoolean(true);
		log.info("Приложение начинает graceful shutdown. Уведомляем WebSocket-клиентов...");

		ChatShutdownMessage shutdownMsg = new ChatShutdownMessage("Server is closing....", 30);

		try {
			messagingTemplate.convertAndSend("/topic/global-shutdown", shutdownMsg);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}