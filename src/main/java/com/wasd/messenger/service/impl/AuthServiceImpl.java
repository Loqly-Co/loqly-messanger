package com.wasd.messenger.service.impl;

import com.wasd.messenger.data.request.LoginRequest;
import com.wasd.messenger.data.response.LoginResponse;
import com.wasd.messenger.entity.PersonMain;
import com.wasd.messenger.entity.Reference;
import com.wasd.messenger.entity.User;
import com.wasd.messenger.exception.UnauthorizedActionException;
import com.wasd.messenger.service.AuthService;
import com.wasd.messenger.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserService     userService;
	private final PasswordEncoder passwordEncoder;

	@Override
	public LoginResponse login(LoginRequest request) {
		Optional<User> optionalUser = userService.findByEmail(request.email());

		if (optionalUser.isEmpty()) {
			throw new UnauthorizedActionException();
		}
		User user = optionalUser.get();

		if (!passwordEncoder.matches(request.password(), user.getPassword())) {
			throw new UnauthorizedActionException();
		}
		
		String username = userService.getUsername(user.getId());
		
		return new LoginResponse(
			user.getId(),
			username,
			user.getRoles().stream()
				.map(Reference::getNote)
				.collect(Collectors.toSet())
		);
	}

	@Override
	public void checkIsCurrentUser(PersonMain personMain) {
		var user = getCurrentUser();
		if (checkEntitiesNotNullAndEquals(personMain, user)) {
			return;
		}
		throw new UnauthorizedActionException();
	}

	@Override
	public User getCurrentUser() {
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		return userService.getById(UUID.fromString(authentication.getName()));
	}

	@Override
	public boolean checkPermission(String permission) {
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()).contains(
			permission);
	}

	private boolean checkEntitiesNotNullAndEquals(PersonMain personMain, User user) {
		return checkPeopleMainAndUsersExistsAndNotNull(personMain, user) && personMain.getUser().getId().equals(user.getId());
	}

	private boolean checkPeopleMainAndUsersExistsAndNotNull(PersonMain personMain, User user) {
		return user != null && personMain != null && personMain.getUser() != null && personMain.getId() != null &&
			   user.getId() != null && personMain.getUser().getId() != null;
	}
}
