package com.softarc.eternal.remote.printing;

import com.softarc.eternal.domain.BrochureStatus;
import com.softarc.eternal.domain.Holiday;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AddPrintingJob {

  private final PrintingClient printingClient;

  public AddPrintingJob(PrintingClient printingClient) {
    this.printingClient = printingClient;
  }

  public BrochureStatus add(Holiday holiday) {
    try {
      this.printingClient.addPrintingJob(
          new AddPrintingJobRequest(
            holiday.getId(),
            holiday.getName(),
            holiday.getDescription()
          )
        );
      return BrochureStatus.CONFIRMED;
    } catch (Exception e) {
      return BrochureStatus.FAILED;
    }
  }
}
