package com.softarc.eternal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softarc.eternal.data.DefaultHolidaysRepository;
import com.softarc.eternal.data.FsHolidaysRepository;
import com.softarc.eternal.data.HolidaysRepository;
import com.softarc.eternal.data.OverlappingCalculator;
import com.softarc.eternal.domain.Holiday;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

  @Bean
  public HolidaysRepository getHolidaysRepository(
    ObjectMapper objectMapper,
    AppProperties appProperties,
    OverlappingCalculator calculator
  ) {
    if ("file".equals(appProperties.getPersistenceType())) {
      return new FsHolidaysRepository(
        objectMapper,
        appProperties.getPersistenceFile()
      );
    } else {
      var holidays = Arrays.asList(
        new Holiday(
          1L,
          "Canada",
          "Visit Rocky Mountains",
          Collections.emptyList()
        ),
        new Holiday(
          2L,
          "China",
          "To the Middle Kingdom",
          Collections.emptyList()
        )
      );
      return new DefaultHolidaysRepository(holidays, calculator);
    }
  }
}
