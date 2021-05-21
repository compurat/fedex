package nl.fedex.pricing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Service

class PricingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PricingService.class);
    private final PricingRepository pricingRepository;
    private final Sender sender;
    private final PricingDtomapper pricingDtomapper;
    private final ObjectMapper objectMapper;
    PricingService(final PricingRepository pricingRepository,
                   final Sender sender,
                   final ObjectMapper objectMapper,
                   final PricingDtomapper pricingDtomapper) {
        this.pricingRepository = pricingRepository;
        this.sender = sender;
        this.objectMapper = objectMapper;
        this.pricingDtomapper = pricingDtomapper;
    }

    Map<String, Double> findPricing(final String pricingIds) {

        Map<String, Double> pricings = new LinkedHashMap<>();
        pricingRepository.findByCountryInOrderByCountryDesc(Arrays.asList(pricingIds.split(","))).forEach(pricingEntity -> {
            if (pricingEntity != null) {
                PricingDto pricingDto = pricingDtomapper.mapper(pricingEntity);
                pricings.put(pricingDto.getCountry(), pricingDto.getAmount());
            }
        });
        System.out.println("pricings " + pricings.size());
        return pricings;
    }

    void findPricingWithMessaging(final String pricingIds) {
        try {
            sender.send(objectMapper.writeValueAsString(findPricing(pricingIds)));
        }

        catch (JsonProcessingException jpe) {
            LOGGER.error("could not parse values", jpe);
        }
    }
}
