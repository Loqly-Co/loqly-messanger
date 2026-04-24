package com.wasd.messenger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultUserDetailsService implements SimpleUserDetailsService {
	
	private final UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		//todo add auth exception
		com.wasd.messenger.entity.User user = userService.findById(UUID.fromString(username)).orElseThrow(
			() -> new RuntimeException("User is not authorized")
		);
		//todo add roles
		return new User(user.getId().toString(), 
						user.getPassword(), 
						user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getNote())).toList());
	}
}
 