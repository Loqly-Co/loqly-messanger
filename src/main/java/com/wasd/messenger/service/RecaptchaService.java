package com.wasd.messenger.service;

import com.google.auth.oauth2.GoogleCredentials;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecaptchaService {
	
	@Value("${credentials.google.path}")
	private String GOOGLE_CREDS_PATH;
	
	private String accessToken;
	
	@PostConstruct
	public void init() {
		GoogleCredentials credentials;
		try(FileInputStream fileInputStream = new FileInputStream(GOOGLE_CREDS_PATH)) {
			credentials = GoogleCredentials.fromStream(fileInputStream)
				.createScoped(List.of("https://www.googleapis.com/auth/playintegrity"));

			credentials.refreshIfExpired();

			this.accessToken = credentials.getAccessToken().getTokenValue();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

    public boolean validate(String token, String ip) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> body = Map.of("integrity_token", token);

		HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

		ResponseEntity<Map> response =
			new RestTemplate().postForEntity(ip, request, Map.class);
		
		log.info("Response status code: {}", response.getStatusCode());
		log.info("Response body: {}", response.getBody());

		System.out.println(response.getBody());
		return true;
    }
}