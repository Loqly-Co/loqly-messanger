package com.wasd.messenger.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TurnstileRequest {

	@JsonProperty("secret")
	private String secret;
	
	@JsonProperty("response")
	private String response;
}
