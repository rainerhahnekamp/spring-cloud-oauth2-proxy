package com.softarc.eternal.remote.printing;

import com.softarc.eternal.domain.BrochureStatus;
import com.softarc.eternal.domain.Holiday;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AddPrintingJob {

  private final WebClient webClient;

  public AddPrintingJob(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.baseUrl("http://localhost:8081").build();
  }

  public BrochureStatus add(Holiday holiday) {
    ResponseEntity<Void> returner = webClient
      .post()
      .uri("/api/order")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(
        new AddPrintingJobRequest(
          holiday.getId(),
          holiday.getName(),
          holiday.getDescription()
        )
      )
      .retrieve()
      .toBodilessEntity()
      .block();

    if (returner.getStatusCode().is2xxSuccessful()) {
      return BrochureStatus.FAILED;
    } else {
      return BrochureStatus.CONFIRMED;
    }
  }
}
