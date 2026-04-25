package com.wasd.messenger.service;

import com.wasd.messenger.data.request.LoginRequest;
import com.wasd.messenger.data.response.LoginResponse;
import com.wasd.messenger.entity.PersonMain;
import com.wasd.messenger.entity.User;

public interface AuthService {
	LoginResponse login(LoginRequest request);

	void checkIsCurrentUser(PersonMain personMain);

	User getCurrentUser();

	boolean checkPermission(String permission);
}
