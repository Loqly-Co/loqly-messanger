package com.wasd.messenger.service;

import com.wasd.messenger.data.RequestVerificationData;

public interface RequestVerificationService {
	
	RequestVerificationData verify(String token);
}
