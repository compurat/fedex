package nl.fedex.tracking;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TrackingController {

    TrackingService trackingService;

    public TrackingController(final TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @GetMapping("/tracking")
    public Map<String, Object> findTracking(@RequestParam("q") final String trackingIds) {
        return trackingService.findTracking(trackingIds);
    }
}
