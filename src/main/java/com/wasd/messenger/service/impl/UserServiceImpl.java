package com.wasd.messenger.service.impl;

import com.wasd.messenger.entity.User;
import com.wasd.messenger.repository.UserRepository;
import com.wasd.messenger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	
	@Override
	public User save(User user) {
		return userRepository.save(user);
	}
	
	@Override
	public Optional<User> findById(UUID id) {
		return userRepository.findById(id);
	}
	
	@Override
	public boolean existsById(UUID id) {
		return userRepository.existsById(id);
	}

	@Override
	public User getById(UUID id) {
		return findById(id).orElseThrow(
			() -> new RuntimeException("User with id " + id + " not found")
		);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByPeopleMain_Email(email);
	}

	@Override
	public Set<String> findUsernamesByIds(Set<UUID> ids) {
		return userRepository.findAllByIdIn(ids);
	}
}
