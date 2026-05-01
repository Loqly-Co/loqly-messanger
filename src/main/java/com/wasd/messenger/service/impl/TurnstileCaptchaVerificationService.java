package com.wasd.messenger.service.impl;

import com.wasd.messenger.client.CloudflareTurnstileClient;
import com.wasd.messenger.data.RequestVerificationData;
import com.wasd.messenger.data.request.TurnstileRequest;
import com.wasd.messenger.data.response.TurnstileResponse;
import com.wasd.messenger.keys.CloudflareTurnstileErrorCode;
import com.wasd.messenger.keys.VerificationCode;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TurnstileCaptchaVerificationService extends CaptchaVerificationService {
	
	@Value("${auth.captcha.secretKey}")
	private String secretKey;
	
	private final CloudflareTurnstileClient cloudflareTurnstileClient;
	
	@Override
	public RequestVerificationData verify(String token) {
		RequestVerificationData verificationData = new RequestVerificationData();
		
		try {
			TurnstileRequest request = new TurnstileRequest();
			request.setResponse(token);
			request.setSecret(secretKey);
			TurnstileResponse response = cloudflareTurnstileClient.verify(request);

			verificationData.setIsSuccessful(response.isSuccess());
			verificationData.setStatus(HttpStatus.OK.value());
			verificationData.setMessage(VerificationCode.SUCCESS);
			
			if (!response.isSuccess()) {
				String errorCode = response.getErrorCodes().getFirst();
				if (CloudflareTurnstileErrorCode.INTERNAL_ERROR.equals(errorCode)) {
					verificationData.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
				} else {
					verificationData.setStatus(HttpStatus.BAD_REQUEST.value());
				}
				verificationData.setMessage(errorCode);
			}
		} catch (FeignException e) {
			log.error(e.getMessage(), e);
			verificationData.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			verificationData.setMessage(e.getMessage());
		}
		return verificationData;
	}
}
