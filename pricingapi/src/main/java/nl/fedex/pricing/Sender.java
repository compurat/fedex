package nl.fedex.pricing;

import nl.fedex.configuration.JmsConfiguration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
class Sender {

    private final RabbitTemplate rabbitTemplate;

    Sender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String values) {
        System.out.println("sending message " + values);
        rabbitTemplate.convertAndSend(JmsConfiguration.TOPIC_EXCHANGE_NAME, "price-value", values);
    }

}