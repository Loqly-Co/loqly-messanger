package com.wasd.messenger.client;

import com.wasd.messenger.config.FeignConfig;
import com.wasd.messenger.data.request.TurnstileRequest;
import com.wasd.messenger.data.response.TurnstileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
	url = "${feign.cloudflare.turnstile.url}", 
	name = "${feign.cloudflare.turnstile.name}",
	configuration = FeignConfig.class)
public interface CloudflareTurnstileClient {

	@PostMapping(
		value = "/turnstile/v0/siteverify",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
	)
	TurnstileResponse verify(@RequestBody TurnstileRequest request);
}
