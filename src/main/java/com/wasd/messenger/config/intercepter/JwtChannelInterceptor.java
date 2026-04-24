package com.wasd.messenger.config.intercepter;

import com.wasd.messenger.service.UserService;
import com.wasd.messenger.utils.JwtTokenProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtChannelInterceptor implements ChannelInterceptor {

	private final JwtTokenProvider   jwtTokenProvider;
	private final UserService        userService;
	private final UserDetailsService userDetailsService;

	@Override
	public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
		if (accessor == null) {
			return message;
		}

		if (StompCommand.CONNECT.equals(accessor.getCommand())) {
			String authHeader = accessor.getFirstNativeHeader("Authorization");

			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				log.warn("Missing or invalid Authorization header in WebSocket CONNECT");
				throw new IllegalArgumentException("Missing JWT token"); // или AuthenticationException
			}

			String token = authHeader.substring(7).trim();

			try {
				if (!jwtTokenProvider.validateToken(token)) {
					log.warn("Invalid JWT token");
					throw new IllegalArgumentException("Invalid JWT token");
				}

				String id = jwtTokenProvider.getUsernameFromToken(token);
				
				if (id == null || id.isEmpty() || !userService.existsById(UUID.fromString(id))) {
					log.warn("User not found with id {} fron JWT", id);
					throw new IllegalArgumentException("Invalid JWT token");
				}
				
				UserDetails userDetails = userDetailsService.loadUserByUsername(id);

				Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

				accessor.setUser(authentication);

				SecurityContextHolder.getContext().setAuthentication(authentication);

				log.info("WebSocket connected successfully for user: {}", id);

			} catch (Exception e) {
				log.error("WebSocket authentication failed", e);
				throw new IllegalArgumentException("WebSocket authentication failed: " + e.getMessage());
			}
		} else if (accessor.getUser() == null) {
			log.warn("No authenticated user for command: {}", accessor.getCommand());
		}

		return message;
	}
}