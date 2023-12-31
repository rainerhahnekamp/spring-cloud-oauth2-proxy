package com.softarc.eternal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softarc.eternal.data.DefaultHolidays;
import com.softarc.eternal.data.FsHolidays;
import com.softarc.eternal.data.Holidays;
import com.softarc.eternal.data.OverlappingCalculator;
import com.softarc.eternal.domain.BrochureStatus;
import com.softarc.eternal.domain.Holiday;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfiguration {

  @Bean
  public Holidays getHolidaysRepository(
    ObjectMapper objectMapper,
    AppProperties appProperties,
    OverlappingCalculator calculator
  ) {
    if ("file".equals(appProperties.getPersistenceType())) {
      return new FsHolidays(objectMapper, appProperties.getPersistenceFile());
    } else {
      List<Holiday> holidays;
      if (appProperties.isPreSeed()) {
        holidays =
          Arrays.asList(
            new Holiday(
              1L,
              "Canada",
              "Visit Rocky Mountains",
              BrochureStatus.REQUESTED,
              null,
              Collections.emptyList()
            ),
            new Holiday(
              2L,
              "China",
              "To the Middle Kingdom",
              BrochureStatus.REQUESTED,
              null,
              Collections.emptyList()
            )
          );
      } else {
        holidays = Collections.emptyList();
      }
      return new DefaultHolidays(holidays, calculator);
    }
  }
}
