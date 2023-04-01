package com.softarc.eternal.domain.converter;

import com.softarc.eternal.domain.BrochureStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BrochureStatusConverter
  implements AttributeConverter<BrochureStatus, String> {

  @Override
  public String convertToDatabaseColumn(BrochureStatus attribute) {
    return attribute.toString();
  }

  @Override
  public BrochureStatus convertToEntityAttribute(String dbData) {
    return BrochureStatus.fromString(dbData);
  }
}
