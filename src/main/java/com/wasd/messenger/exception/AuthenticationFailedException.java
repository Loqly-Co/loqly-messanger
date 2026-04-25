package com.wasd.messenger.exception;

public class AuthenticationFailedException extends ApiException{

    @Override
    public String getMessage() {
        return ApiExceptionConstant.AUTHENTICATION_FAILED_MESSAGE;
    }

    @Override
    public ApiExceptionCode getExceptionCode() {
        return ApiExceptionCode.AUTHENTICATION_FAILED_EXCEPTION;
    }
}