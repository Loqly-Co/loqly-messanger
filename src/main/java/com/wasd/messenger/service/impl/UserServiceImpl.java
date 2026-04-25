package com.wasd.messenger.service.impl;

import com.wasd.messenger.entity.PersonMain;
import com.wasd.messenger.entity.User;
import com.wasd.messenger.exception.EntityNotFoundException;
import com.wasd.messenger.keys.EntityKeys;
import com.wasd.messenger.repository.PersonMainRepository;
import com.wasd.messenger.repository.UserRepository;
import com.wasd.messenger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository       userRepository;
	private final PersonMainRepository personMainRepository;
	
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
	public String getUsername(UUID id) {
		return personMainRepository.findById(id)
			.map(PersonMain::getUsername).orElseThrow(
				() -> new EntityNotFoundException(EntityKeys.PERSON_MAIN, "id", id)
			);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return personMainRepository.findByEmail(email).map(PersonMain::getUser);
	}

	@Override
	public Set<String> findUsernamesByIds(Set<UUID> ids) {
		return personMainRepository.findAllByIdIn(ids)
			.stream()
			.map(PersonMain::getUsername)
			.collect(Collectors.toSet());
	}

	@Override
	public String findUsernameByUser(User user) {
		return personMainRepository.findUsernameByUser(user)
			.map(PersonMain::getUsername)
			.orElseThrow(() -> new EntityNotFoundException(EntityKeys.PERSON_MAIN, "user_id", user.getId()));
	}
}
