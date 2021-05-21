package nl.fedex.pricing;

import org.springframework.stereotype.Component;

@Component
public class Receiver {

    private final PricingService pricingService;

    public Receiver(final PricingService pricingService) {
        this.pricingService = pricingService;
    }

    public void receiveMessage(String parameters) {
        System.out.println("receiving message " + parameters);
        pricingService.findPricingWithMessaging(parameters);
    }

}