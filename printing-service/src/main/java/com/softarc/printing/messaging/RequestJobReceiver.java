package com.softarc.printing.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RequestJobReceiver {

  private final RabbitTemplate rabbitTemplate;

  public RequestJobReceiver(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void processMessage(String message) throws InterruptedException {
    System.out.println(message);
    var parts = message.split(":");
    String eventName = parts[0];
    Long holidayId = Long.parseLong(parts[1]);

    if ("request".equals(eventName)) {
      Thread.sleep(2000);

      this.rabbitTemplate.convertAndSend(
          MessagingConfiguration.exchangeName,
          MessagingConfiguration.routingKey,
          "printed:" + holidayId.toString()
        );
    }
  }
}
