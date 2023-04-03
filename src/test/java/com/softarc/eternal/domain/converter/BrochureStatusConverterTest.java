package com.softarc.eternal.domain.converter;

import com.softarc.eternal.domain.BrochureStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.junit.jupiter.api.Assertions.*;


class BrochureStatusConverterTest {
  @Test
  public void convertFromString() {
    var converter = new BrochureStatusConverter();
    assertThat(converter.convertToEntityAttribute("confirmed")).isEqualTo(BrochureStatus.CONFIRMED);
  }

  @Test
  public void convertToString() {
    var converter = new BrochureStatusConverter();
    assertThat(converter.convertToDatabaseColumn(BrochureStatus.CONFIRMED)).isEqualTo("confirmed");
  }

}
