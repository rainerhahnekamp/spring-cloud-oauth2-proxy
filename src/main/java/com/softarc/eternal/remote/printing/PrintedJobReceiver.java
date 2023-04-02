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
    log.info(message);
    var parts = message.split(":");
    String eventName = parts[0];
    Long holidayId = Long.parseLong(parts[1]);

    if ("printed".equals(eventName)) {
      var holiday = this.holidaysRepository.findById(holidayId).orElseThrow();
      holiday.setBrochureStatus(BrochureStatus.PRINTED);
      this.holidaysRepository.save(holiday);
    }
  }
}
