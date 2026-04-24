package com.wasd.messenger.repository;

import com.wasd.messenger.entity.PeopleMain;
import com.wasd.messenger.entity.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class EmbeddedUserRepository implements UserRepository {

	private final Map<UUID, User> users = new ConcurrentHashMap<>();

	@Override
	public boolean existsById(UUID id) {
		return users.containsKey(id);
	}

	@Override
	public User save(User user) {
		return users.put(user.getId(), user);
	}

	@Override
	public Optional<User> findById(UUID id) {
		return Optional.ofNullable(users.get(id));
	}

	@Override
	public Optional<User> findByPeopleMain_Email(String email) {
		return users.values().stream().filter(u -> {
			if (u.getPeopleMain() == null) {
				return false;
			}
			return email.equals(u.getPeopleMain().getEmail());
		}).findFirst();
	}

	@Override
	public Set<String> findAllByIdIn(Set<UUID> ids) {
		return ids.stream().map(id -> users.get(id).getPeopleMain().getUsername()).collect(Collectors.toSet());
	}

	@PostConstruct
	public void init() {
		UUID userId = UUID.randomUUID();
		UUID user2Id = UUID.randomUUID();
		users.put(userId, new User(
			userId,
			"$2a$12$i8yQboAwUxrlzkhikeahsevW/icH23sMsftHay0IY1UP8OJDUKleG",
			new PeopleMain(
				1L,
				"wasd_0",
				"example@example.com",
				"John",
				"Doe"
			),
			new HashSet<>(), 
			new HashSet<>()
		));
		users.put(user2Id, new User(
			user2Id,
			"$2a$12$i8yQboAwUxrlzkhikeahsevW/icH23sMsftHay0IY1UP8OJDUKleG",
			new PeopleMain(
				2L, 
				"test",
				"t@t.com",
				"Steve",
				"Minecraft"
							   ), 
			new HashSet<>(), 
			new HashSet<>()));
	}
}
