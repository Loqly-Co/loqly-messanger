package com.wasd.messenger.exception;

public class UnauthorizedActionException extends ApiException{
    @Override
    public String getMessage() {
        return ApiExceptionConstant.UNAUTHORIZED_ACTION_EXCEPTION;
    }

    @Override
    public ApiExceptionCode getExceptionCode() {
        return ApiExceptionCode.UNAUTHORIZED_ACTION_EXCEPTION;
    }
}
