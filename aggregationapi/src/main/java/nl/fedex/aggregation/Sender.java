package nl.fedex.aggregation;

import nl.fedex.configuration.JmsConfiguration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class Sender {

    private final RabbitTemplate rabbitTemplate;

    public Sender( RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(final String routingKey, final String parameters) {
        System.out.println("Sending message..." + routingKey + " " + parameters);
        rabbitTemplate.convertAndSend(JmsConfiguration.TOPIC_EXCHANGE_NAME, routingKey, parameters);
    }
}