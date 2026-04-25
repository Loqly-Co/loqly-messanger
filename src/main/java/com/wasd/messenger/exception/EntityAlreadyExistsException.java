package com.wasd.messenger.exception;

import com.wasd.messenger.keys.EntityKeys;
import lombok.Getter;

import java.util.Map;

/**
 * Исключения для случаев, когда сущность уже создана с заданным ключом
 */
@Getter
public class EntityAlreadyExistsException extends EntityOperationException {

    public EntityAlreadyExistsException(EntityKeys entityKeys) {
        super(entityKeys);
    }

    public EntityAlreadyExistsException(EntityKeys entityKeys, String key, Object value) {
        super(entityKeys, key, value);
    }

    public EntityAlreadyExistsException(EntityKeys entityKeys, String key, Object value, String key2, Object value2) {
        super(entityKeys, key, value, key2, value2);
    }

    public EntityAlreadyExistsException(EntityKeys entityKeys, String key, Object value, String key2, Object value2, String key3, Object value3) {
        super(entityKeys, key, value, key2, value2, key3, value3);
    }

    public EntityAlreadyExistsException(EntityKeys entityKeys, Map<String, Object> keys) {
        super(entityKeys, keys);
    }

    @Override
    public String getMessage() {
        return "Сущность %s уже создана с ключём %s".formatted(entityDescriptor(), getEntityKeys().toString());
    }
}