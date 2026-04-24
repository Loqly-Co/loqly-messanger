package com.wasd.messenger.controller;

import com.wasd.messenger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final OnlineUsersWebsocketController onlineUsersWebsocketController;
	private final UserService userService;

    @GetMapping("/online")
    public Set<String> getOnlineUsers() {
        return userService.findUsernamesByIds(
			onlineUsersWebsocketController.getOnlineUsers().stream().map(UUID::fromString).collect(Collectors.toSet())
		);
    }
}