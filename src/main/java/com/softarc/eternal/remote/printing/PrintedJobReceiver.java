package com.softarc.eternal.remote.printing;

import com.softarc.eternal.data.HolidaysRepository;
import com.softarc.eternal.domain.BrochureStatus;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class PrintedJobReceiver {

  private final HolidaysRepository holidaysRepository;

  PrintedJobReceiver(HolidaysRepository holidaysRepository) {
    this.holidaysRepository = holidaysRepository;
  }

  public void processMessage(String message) {
    Long holidayId = Long.parseLong(message);
    this.holidaysRepository.findById(holidayId)
      .ifPresentOrElse(
        holiday -> {
          holiday.setBrochureStatus(BrochureStatus.PRINTED);
          this.holidaysRepository.save(holiday);
        },
        () -> {
          log.warning("Could not find Holiday with ID " + holidayId);
        }
      );
  }
}
