package nl.fedex.tracking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class TrackingServiceTest {
    private static final String TRACKING_TYPE1 = "NEW";
    private static final String TRACKING_TYPE2 = "COLLECTING";
    private static final String TRACKING_ID_1 = "1";
    private static final String TRACKING_ID_2 = "2";
    private static final String TRACKING_ENTITY_IDS = TRACKING_ID_1 + "," + TRACKING_ID_2;

    private  ArrayList<String> trackingIds;
    @Mock
    private TrackingRepository trackingRepository;
    @InjectMocks
    private TrackingService trackingService;

    @BeforeEach
    public void init() {
        trackingIds = new ArrayList<>();
        trackingIds.add(TRACKING_ID_1);
        trackingIds.add(TRACKING_ID_2);
    }

    @Test
    public void testFindTracking() throws JsonProcessingException {
        TrackingEntity trackingEntity1 = createTrackingEntity(TRACKING_ID_1, TRACKING_TYPE1);
        TrackingEntity trackingEntity2 = createTrackingEntity(TRACKING_ID_2, TRACKING_TYPE2);
        List<TrackingEntity> trackingEntities = new ArrayList<>();
        trackingEntities.add(trackingEntity1);
        trackingEntities.add(trackingEntity2);

        Mockito.when(trackingRepository.findByTrackingIdIn(trackingIds)).thenReturn(trackingEntities);
        ObjectMapper objectMapper = new ObjectMapper();
        Assertions.assertEquals(createConvertedTrackingEntity(), objectMapper.writeValueAsString(trackingService.findTracking(TRACKING_ENTITY_IDS)));
    }

    @Test
    public void testFindTrackingNotFound() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        Assertions.assertEquals("{}", objectMapper.writeValueAsString(trackingService.findTracking(TRACKING_ENTITY_IDS)));
    }

    private TrackingEntity createTrackingEntity(final String trackingId, final String trackingTypes) {
        TrackingEntity trackingEntity = new TrackingEntity();
        trackingEntity.setTrackingId(trackingId);
        trackingEntity.setTrackingTypes(trackingTypes);
        return trackingEntity;
   }

    private String createConvertedTrackingEntity() throws JsonProcessingException {
       ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> convertedShipmentEntity = new HashMap<>();
        convertedShipmentEntity.put("1", TRACKING_TYPE1);
        convertedShipmentEntity.put("2", TRACKING_TYPE2);
        return objectMapper.writeValueAsString(convertedShipmentEntity);
    }
}
