package nl.fedex.configuration;

import nl.fedex.aggregation.Receiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class JmsConfiguration {

    public static final String TOPIC_EXCHANGE_NAME = "FEDEX";

    public static final String PRICE_PARAMETER_QUEUE = "PRICE_PARAMETER_QUEUE";
    public static final String TRACKING_PARAMETER_QUEUE = "TRACKING_PARAMETER_QUEUE";
    public static final String SHIPPING_PARAMETER_QUEUE = "SHIPPING_PARAMETER_QUEUE";

    @Bean
    Queue priceQueue() {
        return new Queue(PRICE_PARAMETER_QUEUE, false);
    }
    @Bean
    Queue packageQueue() {
        return new Queue(TRACKING_PARAMETER_QUEUE, false);
    }
    @Bean
    Queue shippingQueue() {
        return new Queue(SHIPPING_PARAMETER_QUEUE, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }


    @Bean
    List<Binding> bindings() {

        return Arrays.asList(BindingBuilder.bind(priceQueue()).to(exchange()).with("price-value"),
                BindingBuilder.bind(packageQueue()).to(exchange()).with("track-value"),
                BindingBuilder.bind(shippingQueue()).to(exchange()).with("shipments-value"));

    }


    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(PRICE_PARAMETER_QUEUE, SHIPPING_PARAMETER_QUEUE, TRACKING_PARAMETER_QUEUE);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

}
