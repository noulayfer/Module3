package com.fedorenko.util;

import javax.persistence.AttributeConverter;
import java.util.Optional;

public class FloatConverter implements AttributeConverter<Float, String> {

    @Override
    public String convertToDatabaseColumn(Float attribute) {
        if (attribute == null) {
            return null;
        }
        return String.format("%.2f", attribute);
    }

    @Override
    public Float convertToEntityAttribute(String s) {
        return Optional.ofNullable(s)
                .map(Float::valueOf)
                .orElse(null);
    }
}
