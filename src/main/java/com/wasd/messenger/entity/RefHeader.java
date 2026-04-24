package com.wasd.messenger.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefHeader {

	public static final String ENTITY_NAME = "ref_header";

	private Integer id;

	private String name;

	private String comment;

	public boolean is(Integer code) {
		if (Objects.isNull(code)) {
			return false;
		}
		return this.id.equals(code);
	}
}
