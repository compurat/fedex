package nl.fedex.aggregation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AggregationController {

    @Autowired
    AggregationService aggregationService;

    @GetMapping("/aggregation")
    public Map<String, Map<String, ? extends Object>> aggregateData(@RequestParam("pricing") final String pricing,
                                                     @RequestParam("track") final String track,
                                                     @RequestParam("shipments") final String shipments) {
        return aggregationService.aggregate(pricing, track, shipments);
    }

}
