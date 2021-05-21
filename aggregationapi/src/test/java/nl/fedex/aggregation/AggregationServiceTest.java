package nl.fedex.aggregation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class AggregationServiceTest {
    @Mock
    private Sender sender;
    @InjectMocks
    private AggregationService aggregationService;

    @Test
    public void testAggregate() {
        List<String> pricingExecutions = new ArrayList<>();
        List<String> trackExecutions = new ArrayList<>();
        List<String> shippingExecutions = new ArrayList<>();
        Map<String, List<String>> allExecutions = new HashMap<>();
        aggregationService.init();
        String pricing = "NL,CN";
        String trackAndShipments = "109347263,123456891";
        aggregationService.aggregate(pricing, trackAndShipments, trackAndShipments);
        pricingExecutions.add(pricing);
        trackExecutions.add(trackAndShipments);
        shippingExecutions.add(trackAndShipments);
        Mockito.verify(sender, Mockito.times(0)).send(Mockito.anyString(), Mockito.anyString());
        aggregationService.aggregate(pricing, trackAndShipments, trackAndShipments);
        pricingExecutions.add(pricing);
        trackExecutions.add(trackAndShipments);
        shippingExecutions.add(trackAndShipments);
        Mockito.verify(sender, Mockito.times(0)).send(Mockito.anyString(), Mockito.anyString());
        aggregationService.aggregate(pricing, trackAndShipments, trackAndShipments);
        pricingExecutions.add(pricing);
        trackExecutions.add(trackAndShipments);
        shippingExecutions.add(trackAndShipments);
        Mockito.verify(sender, Mockito.times(0)).send(Mockito.anyString(), Mockito.anyString());
        aggregationService.aggregate(pricing, trackAndShipments, trackAndShipments);
        pricingExecutions.add(pricing);
        trackExecutions.add(trackAndShipments);
        shippingExecutions.add(trackAndShipments);
        Mockito.verify(sender, Mockito.times(0)).send(Mockito.anyString(), Mockito.anyString());
        pricingExecutions.add(pricing);
        trackExecutions.add(trackAndShipments);
        shippingExecutions.add(trackAndShipments);
        allExecutions.put("pricing", pricingExecutions);
        allExecutions.put("shipments", shippingExecutions);
        allExecutions.put("track", trackExecutions);
        ReflectionTestUtils.setField(aggregationService, "allExecutions", allExecutions);
        aggregationService.aggregate(pricing, trackAndShipments, trackAndShipments);
        Mockito.verify(sender, Mockito.times(15)).send(Mockito.anyString(), Mockito.anyString());
    }
}