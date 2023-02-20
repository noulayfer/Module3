package com.fedorenko.util;

import javax.persistence.AttributeConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class LocalDateConverter implements AttributeConverter<LocalDate, String> {

    @Override
    public String convertToDatabaseColumn(final LocalDate attribute) {
        return Optional.ofNullable(attribute)
                .map(date -> date.format(DateTimeFormatter.ISO_LOCAL_DATE))
                .orElse(null);
    }

    @Override
    public LocalDate convertToEntityAttribute(final String dbData) {
        return Optional.ofNullable(dbData)
                .map(date -> LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE))
                .orElse(null);
    }
}