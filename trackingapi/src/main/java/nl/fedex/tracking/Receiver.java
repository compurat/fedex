package nl.fedex.tracking;

import org.springframework.stereotype.Component;

@Component
public class Receiver {

    private final TrackingService trackingService;

    public Receiver(final TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    public void receiveMessage(String parameters) {
        System.out.println("Received parameters " + parameters);
        trackingService.findTrackingWithMessaging(parameters);
    }

}