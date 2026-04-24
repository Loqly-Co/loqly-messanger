package com.wasd.messenger.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reference {

	private Long id;

	private String value;

	private Integer code;

	private String note;

	private RefHeader refHeader;

	private String comment;
}
