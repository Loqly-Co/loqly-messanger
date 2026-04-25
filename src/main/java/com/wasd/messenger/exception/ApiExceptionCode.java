package com.wasd.messenger.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiExceptionCode {

    NOT_FOUND_EXCEPTION("apiExceptions.notFoundException"),
    TWO_FACTOR_FAILED_EXCEPTION("apiExceptions.twoFactorFailedException"),
    ENTITY_ALREADY_EXISTS_EXCEPTION("apiExceptions.entityAlreadyExistsException"),
    AUTHENTICATION_FAILED_EXCEPTION("apiExceptions.authenticationFailedException"),
    MISSING_REQUEST_HEADER_EXCEPTION("apiExceptions.missingRequestHeaderException"),
    UNAUTHORIZED_ACTION_EXCEPTION("apiExceptions.unauthorizedActionException");

    private final String code;
}