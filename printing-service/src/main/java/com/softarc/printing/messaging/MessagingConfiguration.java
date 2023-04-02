package com.softarc.printing.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfiguration {

  public static final String exchangeName = "printing-events-exchange";
  public static final String queueName = "printing-events-queue";
  public static final String routingKey = exchangeName + "." + queueName;

  @Bean
  Queue getQueue() {
    return new Queue(queueName, false);
  }

  @Bean
  TopicExchange getExchange() {
    return new TopicExchange(exchangeName);
  }

  @Bean
  Binding getBinding(Queue queue, TopicExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(routingKey);
  }

  @Bean
  MessageListenerAdapter listenerAdapter(
    RequestJobReceiver requestJobReceiver
  ) {
    return new MessageListenerAdapter(requestJobReceiver, "processMessage");
  }

  @Bean
  SimpleMessageListenerContainer getContainer(
    ConnectionFactory connectionFactory,
    MessageListenerAdapter listenerAdapter
  ) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(queueName);
    container.setMessageListener(listenerAdapter);

    return container;
  }
}
