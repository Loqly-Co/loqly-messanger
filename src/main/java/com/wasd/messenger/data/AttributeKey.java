package com.wasd.messenger.data;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.util.Map;

@Getter
@Setter(value = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AttributeKey {

    private Map<String, Object> attributes;

    public static AttributeKey create(@NonNull Map<String, Object> attributes) {
        AttributeKey attributeKey = new AttributeKey();
        attributeKey.setAttributes(attributes);
        return attributeKey;
    }

    public boolean isCompound() {
        return attributes.size() > 1;
    }

    public boolean isSimple() {
        return attributes.size() == 1;
    }

    public boolean isEmpty() {
        return attributes.isEmpty();
    }
}