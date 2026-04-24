package com.wasd.messenger.repository;

import com.wasd.messenger.entity.User;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserRepository {
	
	boolean existsById(UUID id);

	User save(User user);

	Optional<User> findById(UUID id);

	Optional<User> findByPeopleMain_Email(String email);

	Set<String> findAllByIdIn(Set<UUID> ids);
}
