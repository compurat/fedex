package nl.fedex.pricing;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;

@RestController
public class PricingController {
    PricingService pricingService;

    public PricingController(final PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @GetMapping("/pricing")
    public Map<String, Double> findPricing(@RequestParam("q") final String countries) {
        return pricingService.findPricing(countries);
    }

}
