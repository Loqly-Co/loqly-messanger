package com.wasd.messenger.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@RequiredArgsConstructor
public class TwoFactorAuthenticationException extends ApiException{
    
    private final String otpToken;
    private final ZonedDateTime expiresIn;

    @Override
    public String getMessage() {
        return ApiExceptionConstant.TWO_FACTOR_AUTHENTICATION_FAILED_MESSAGE;
    }

    @Override
    public ApiExceptionCode getExceptionCode() {
        return ApiExceptionCode.TWO_FACTOR_FAILED_EXCEPTION;
    }

}
