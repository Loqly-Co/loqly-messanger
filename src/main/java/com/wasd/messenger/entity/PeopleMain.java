package com.wasd.messenger.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@AllArgsConstructor
@NoArgsConstructor
public class PeopleMain {
	private Long id;
	private String username;
	private String email;
	private String firstName;
	private String lastName;
}
