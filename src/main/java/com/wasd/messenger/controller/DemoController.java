package com.wasd.messenger.controller;

import com.wasd.messenger.service.RecaptchaService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DemoController {

	private final RecaptchaService recaptchaService;
	
	@PostMapping("/submit")
	public ResponseEntity<?> submit(@RequestParam("g-recaptcha-response") String token,
									HttpServletRequest request) {

		String ip = request.getRemoteAddr();

		if (!recaptchaService.validate(token, ip)) {
			return ResponseEntity.badRequest().body("Captcha failed");
		}

		return ResponseEntity.ok("OK");
	}
}
