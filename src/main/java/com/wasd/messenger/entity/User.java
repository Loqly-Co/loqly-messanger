package com.wasd.messenger.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	private UUID   id;
	
	private String password;
	
	private PeopleMain peopleMain;

	private Set<Reference> roles = new HashSet<>();

	private Set<Reference> Authorities = new HashSet<>();
}
