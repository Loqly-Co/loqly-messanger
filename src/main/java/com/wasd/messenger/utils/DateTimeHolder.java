package com.wasd.messenger.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
public class DateTimeHolder {
	
	private final Clock clock;
	
	public ZonedDateTime now() {
		return ZonedDateTime.now(clock);
	}
	
	public ZonedDateTime userNow() {
		//todo: изменить на получение пользовательского
		return ZonedDateTime.now(ZoneId.systemDefault());
	}
}
