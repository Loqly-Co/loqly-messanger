package com.wasd.messenger.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class ApiException extends RuntimeException {

    private String errorDetails;

    public ApiException(String errorDetails) {
        this.errorDetails = errorDetails;
    }

    public abstract String getMessage();

    public String getErrorDetails() {
        return this.errorDetails;
    }

    public abstract ApiExceptionCode getExceptionCode();

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }
}
