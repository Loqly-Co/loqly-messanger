package com.wasd.messenger.service;

import com.wasd.messenger.entity.User;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserService {

	User save(User user);

	Optional<User> findById(UUID uuid);

	boolean existsById(UUID id);

	User getById(UUID id);

	Optional<User> findByEmail(String email);

	Set<String> findUsernamesByIds(Set<UUID> ids);
}
