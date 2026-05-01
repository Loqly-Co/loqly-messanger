package com.wasd.messenger.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestVerificationData {

	private Integer status;
	private Boolean isSuccessful;
	private String  message;
}
