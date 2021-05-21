package nl.fedex.aggregation;

import org.springframework.stereotype.Component;

@Component
public class Receiver {

    private final AggregationService aggregationService;

    Receiver(final AggregationService aggregationService) {
        this.aggregationService = aggregationService;
    }

    public void receiveMessage(String message) {
        System.out.println("receiving message " + message);
        aggregationService.setMessage(message);
    }
}