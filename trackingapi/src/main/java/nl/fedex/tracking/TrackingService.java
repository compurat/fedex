package nl.fedex.tracking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
class TrackingService {

    private final TrackingRepository trackingRepository;
    private final Sender sender;
    private final ObjectMapper objectMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(TrackingService.class);
    TrackingService(final TrackingRepository trackingRepository, final Sender sender, final ObjectMapper objectMapper) {
        this.trackingRepository = trackingRepository;
        this.sender = sender;
        this.objectMapper = objectMapper;
    }

    Map<String, Object> findTracking(final String trackingEntityIds) {
        Map<String, Object> trackings = new HashMap<>();
        trackingRepository.findByTrackingIdIn(Arrays.asList(trackingEntityIds.split(","))).forEach(trackingEntity -> {
            if (trackingEntity != null) {
                trackings.put(trackingEntity.getTrackingId(), trackingEntity.getTrackingTypes());
            }
        });
        return trackings;
    }

    void findTrackingWithMessaging(final String trackingEntityIds) {

        try {
            sender.send(objectMapper.writeValueAsString(findTracking(trackingEntityIds)));
        }
        catch (JsonProcessingException jpe) {
            LOGGER.error("could not parse json", jpe);
        }
    }
}
