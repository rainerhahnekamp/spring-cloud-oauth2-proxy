package com.softarc.eternal.remote.printing;

import com.softarc.eternal.domain.BrochureStatus;
import com.softarc.eternal.domain.Holiday;
import com.softarc.eternal.messaging.MessagingConfiguration;
import java.util.HashMap;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AddPrintingJob {

  private final RabbitTemplate rabbitTemplate;

  public AddPrintingJob(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public BrochureStatus add(Holiday holiday) {
    this.rabbitTemplate.convertAndSend(
        MessagingConfiguration.exchangeName,
        MessagingConfiguration.routingKey,
        "request:" + holiday.getId()
      );
    return BrochureStatus.REQUESTED;
  }
}
