package com.wasd.messenger.exception;

import com.wasd.messenger.data.AttributeKey;
import com.wasd.messenger.keys.EntityKeys;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public abstract class EntityOperationException extends ServiceException {

    /**
     * Информация о сущности
     */
    private final EntityKeys entityKeys;

    /**
     * Информация о ключах
     */
    private final AttributeKey attributeKey;

    public EntityOperationException(@NonNull EntityKeys entityKeys) {
        this.entityKeys = entityKeys;
        this.attributeKey = AttributeKey.create(Collections.emptyMap());
    }

    public EntityOperationException(@NonNull EntityKeys entityKeys, @NonNull String key, @NonNull Object value) {
        this.entityKeys = entityKeys;
        this.attributeKey = AttributeKey.create(Map.of(key, value));
    }

    public EntityOperationException(@NonNull EntityKeys entityKeys,
                                    @NonNull String key, @NonNull Object value,
                                    @NonNull String key2, @NonNull Object value2) {
        this.entityKeys = entityKeys;
        this.attributeKey = AttributeKey.create(Map.of(
                key, value,
                key2, value2)
        );
    }

    public EntityOperationException(@NonNull EntityKeys entityKeys,
                                    @NonNull String key, @NonNull Object value,
                                    @NonNull String key2, @NonNull Object value2,
                                    @NonNull String key3, @NonNull Object value3) {
        this.entityKeys = entityKeys;
        this.attributeKey = AttributeKey.create(Map.of(
                key, value,
                key2, value2,
                key3, value3)
        );
    }

    public EntityOperationException(@NonNull EntityKeys entityKeys, @NonNull Map<String, Object> attributes) {
        this.entityKeys = entityKeys;
        this.attributeKey = AttributeKey.create(attributes);
    }

    public String entityDescriptor() {
        StringBuilder builder = new StringBuilder();
        builder.append("[")
                .append(entityKeys.getDescription())
                .append("]")
                .append(" с ключом ")
                .append("[");

        String attributes = attributeKey.getAttributes().entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(","));

        builder.append(attributes)
                .append("]");

        return builder.toString();
    }
}