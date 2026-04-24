package com.wasd.messenger.controller;

import com.wasd.messenger.data.ChatFullData;
import com.wasd.messenger.data.CreateChatData;
import com.wasd.messenger.data.request.ChatFullDataRequest;
import com.wasd.messenger.data.request.CreateChatRequest;
import com.wasd.messenger.data.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {
	
	public RestResponse<ChatFullData> getChatById(@RequestBody ChatFullDataRequest request) {
		//todo
		return RestResponse.failure(null);
	}
	
	public RestResponse<CreateChatData> createChat(@RequestBody CreateChatRequest request) {
		//todo 
		return RestResponse.failure(null);
	}
}
