package com.wasd.messenger.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Service
@AllArgsConstructor
@NoArgsConstructor
@Table(name = PersonMain.ENTITY_NAME)
@Entity
public class PersonMain extends AuditableEntity {
	
	public static final String ENTITY_NAME = "person_main";

	@Id
	private UUID id;

	@Column(name = "username", nullable = false, unique = true)
	private String username;
	
	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "birthdate")
	private LocalDate birthdate;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "id")
	private User user;
}
