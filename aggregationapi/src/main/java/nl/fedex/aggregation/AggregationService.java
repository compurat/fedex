package nl.fedex.aggregation;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;

@Service
class AggregationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AggregationService.class);

    private final Sender sender;
    //those 2 classmembers are needed for caching.
    private String message;
    private Map<String, List<String>> allExecutions;

    @PostConstruct
    public void init() {
        allExecutions = new HashMap<>();

    }

    AggregationService(final Sender sender) {
        this.sender = sender;
    }

    Map<String, Map<String, ?>> aggregate(final String pricing, final String track, final String shipments) {
        Map<String, Map<String, ?>> aggregator = new LinkedHashMap<>();
        List<String> pricingExecutions = new ArrayList<>();
        List<String> trackExecutions = new ArrayList<>();
        List<String> shippingExecutions = new ArrayList<>();

        String pricingParameter = "pricing";
        String trackParameter = "track";
        String shippingParameter = "shipments";
        if (getAllExecutions().get(pricingParameter) != null) {
            pricingExecutions = allExecutions.get(pricingParameter);
            shippingExecutions = allExecutions.get(shippingParameter);
            trackExecutions = allExecutions.get(trackParameter);
        }

        if (pricingExecutions.size() < 5) {
            pricingExecutions.add(pricing);
            trackExecutions.add(track);
            shippingExecutions.add(shipments);
            allExecutions.put(pricingParameter, pricingExecutions);
            allExecutions.put(shippingParameter, shippingExecutions);
            allExecutions.put(trackParameter, trackExecutions);
            setAllExecutions(allExecutions);
        }
        else {
            Pricing pricingData = new Pricing();
            pricingData.setParameterName(pricingParameter);
            pricingData.setParameters(pricingExecutions);
            pricingData.setRoutingKey("pricing-parameters");
            Track trackData = new Track();
            trackData.setParameterName(trackParameter);
            trackData.setParameters(trackExecutions);
            trackData.setRoutingKey("tracking-parameters");
            Shippings shippingsData = new Shippings();
            shippingsData.setParameterName(shippingParameter);
            shippingsData.setParameters(shippingExecutions);
            shippingsData.setRoutingKey("shipment-parameters");
            aggregator = scheduleRequests(pricingData, trackData, shippingsData);
            pricingExecutions.removeAll(pricingExecutions);
            trackExecutions.removeAll(trackExecutions);
            shippingExecutions.removeAll(shippingExecutions);
            clearAllExecutionsFromCache();
        }

        return aggregator;
    }


    private Map<String, ?> getValues(String routingKey, String requestParameters) {
        Gson gson = new Gson();
        Map<String, Double> values = new LinkedHashMap<>();
        sender.send(routingKey, requestParameters);
        try {
            TimeUnit.SECONDS.sleep(3);
        }
        catch (InterruptedException ie) {
            LOGGER.error("TimeUnit.SECONDS.sleep was interrupted", ie);
        }
        String trackMessage = getMessageFromCache();
        if ((trackMessage != null && !trackMessage.isEmpty()) && !trackMessage.equals(requestParameters)) {
            values = gson.fromJson(trackMessage, Map.class);
        }
        return values;
    }

    private Map<String, Map<String, ?>> scheduleRequests(Pricing pricing, Track track, Shippings shippings) {

        ScheduledExecutorService scheduledExecutorService =
                Executors.newScheduledThreadPool(1);
        ScheduledFuture <Map<String, Map<String, ? extends Object>>> scheduledFuture =
                scheduledExecutorService.schedule(new Callable() {
                                                      public Object call() throws Exception {
                                                          Map<String, Map<String, ? extends Object>> aggregator = new LinkedHashMap<>();
                                                          pricing.parameters.forEach(execution -> {
                                                              aggregator.put(pricing.getParameterName(), getValues(pricing.getRoutingKey(), execution));
                                                          });
                                                          track.parameters.forEach(execution -> {
                                                              aggregator.put(track.getParameterName(), getValues(track.getRoutingKey(), execution));
                                                          });
                                                          shippings.parameters.forEach(execution -> {
                                                              aggregator.put(shippings.getParameterName(), getValues(shippings.getRoutingKey(), execution));
                                                          });


                                                          return aggregator;
                                                      }
                                                  },
                        5,
                        TimeUnit.SECONDS);
        Map<String, Map<String, ? extends Object>> aggregator = null;

        try {
            aggregator = (Map<String, Map<String, ?>>) scheduledFuture.get();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }
        return aggregator;
    }

    @CachePut(value = "executions", key = "#root.target.EXECUTION_KEY")
    public void setAllExecutions(Map<String, List<String>> allExecutions) {
        this.allExecutions = allExecutions;
    }

    @Cacheable(value = "executions", key = "#root.target.EXECUTION_KEY")
    public Map<String, List<String>> getAllExecutions() {
        return allExecutions;
    }

    @CacheEvict(value = "executions", key = "#root.target.EXECUTION_KEY")
    public void clearAllExecutionsFromCache() {
        allExecutions.clear();
    }

    @Cacheable(value = "messages", key = "#root.target.KEY")
    public String getMessageFromCache() {
        return message;
    }


    @CachePut(value = "messages", key = "#root.target.KEY")
    public void setMessage(final String message) {
        this.message = message;
    }
}