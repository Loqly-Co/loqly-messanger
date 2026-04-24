package com.wasd.messenger.service.impl;

import com.wasd.messenger.data.request.LoginRequest;
import com.wasd.messenger.data.response.LoginResponse;
import com.wasd.messenger.entity.PeopleMain;
import com.wasd.messenger.entity.Reference;
import com.wasd.messenger.entity.User;
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
		//todo exception
		if (optionalUser.isEmpty()) {
			throw new RuntimeException("Email or password invalid");
		}
		User user = optionalUser.get();
		//todo exception
		if (!passwordEncoder.matches(request.password(), user.getPassword())) {
			throw new RuntimeException("Email or password invalid");
		}
		return new LoginResponse(
			user.getId(),
			user.getPeopleMain().getUsername(),
			user.getRoles().stream()
				.map(Reference::getNote)
				.collect(Collectors.toSet())
		);
	}

	@Override
	public void checkIsCurrentUser(PeopleMain peopleMain) {
		var user = getCurrentUser();
		if (checkEntitiesNotNullAndEquals(peopleMain, user)) {
			return;
		}
		//todo exception
		throw new RuntimeException("Current user is not current");
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

	private boolean checkEntitiesNotNullAndEquals(PeopleMain peopleMain, User user) {
		return checkPeopleMainAndUsersExistsAndNotNull(peopleMain, user) && user.getPeopleMain().getId().equals(peopleMain.getId());
	}

	private boolean checkPeopleMainAndUsersExistsAndNotNull(PeopleMain peopleMain, User user) {
		return user != null && peopleMain != null && user.getPeopleMain() != null && peopleMain.getId() != null &&
			   user.getPeopleMain().getId() != null;
	}
}
