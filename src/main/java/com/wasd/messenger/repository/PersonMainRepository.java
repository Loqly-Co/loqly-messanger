package com.wasd.messenger.repository;

import com.wasd.messenger.entity.PersonMain;
import com.wasd.messenger.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface PersonMainRepository extends JpaRepository<PersonMain, UUID> {

	Optional<PersonMain> findByEmail(String email);
	
	List<PersonMain> findAllByIdIn(Set<UUID> ids);
	
	Optional<PersonMain> findUsernameByUser(User user);
}
