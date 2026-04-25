package com.wasd.messenger.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = RefHeader.ENTITY_NAME)
@Entity
public class RefHeader {

	public static final String ENTITY_NAME = "ref_header";

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private Integer id;


	@Column(name = "name")
	private String name;

	@Column(name = "comment")
	private String comment;

	public boolean is(Integer code) {
		if (Objects.isNull(code)) {
			return false;
		}
		return this.id.equals(code);
	}
}
