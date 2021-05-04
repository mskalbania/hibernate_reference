package com.hibernate.example.model.converter;

import com.hibernate.example.model.MonetaryAmount;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MonetaryAmountTypeConverter implements AttributeConverter<MonetaryAmount, String> {

    @Override
    public String convertToDatabaseColumn(MonetaryAmount attribute) {
        return attribute != null ? attribute.toString() : null;
    }

    @Override
    public MonetaryAmount convertToEntityAttribute(String dbData) {
        return dbData != null ? MonetaryAmount.fromString(dbData) : null;
    }
}
