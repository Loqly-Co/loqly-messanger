package com.wasd.messenger.exception;

import lombok.Getter;

/**
 * Исключение для случаев, когда сущность не найдена
 */
@Getter
public class NotFoundException extends ApiException {

    private final String entityName;

    private final Object id;

    public NotFoundException(String entityName, Object id) {
        this.entityName = entityName;
        this.id = id;
    }

    @Override
    public String getMessage() {
        return ApiExceptionConstant.NOT_FOUND_MESSAGE;
    }

    @Override
    public ApiExceptionCode getExceptionCode() {
        return ApiExceptionCode.NOT_FOUND_EXCEPTION;
    }
}