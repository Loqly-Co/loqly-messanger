package com.wasd.messenger.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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
@Entity
@Table(name = User.ENTITY_NAME)
public class User extends AuditableEntity {

	public static final String ENTITY_NAME = "user";
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name = "id", columnDefinition = "uuid")
	private UUID id;

	@Column(name = "password")
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "user_role",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "role")
	)
	private Set<Reference> roles = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "user_authority",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "authority")
	)
	private Set<Reference> Authorities = new HashSet<>();

	@Column(name = "timezone")
	private String timezone;
}
