package nl.fedex.pricing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PricingServiceTest {

    public static final double AMOUNT = 1234D;
    public static final String COUNTRY = "NL";
    @Mock
    PricingRepository pricingRepository;
    @Mock
    private PricingDtomapper pricingDtomapper;
    @InjectMocks
    PricingService pricingService;

    @Test
    public void testFindPricing() {
        List<PricingEntity> pricingEntities = createPricingEntities();
        Mockito.when(pricingRepository.findByCountryInOrderByCountryDesc(Collections.singletonList(COUNTRY))).thenReturn(pricingEntities);
        Mockito.when(pricingDtomapper.mapper(Mockito.any(PricingEntity.class))).thenReturn(createPricingDto());
        Assertions.assertEquals(AMOUNT, pricingService.findPricing(COUNTRY).get(COUNTRY));
    }

    private List<PricingEntity> createPricingEntities() {
        PricingEntity pricingEntity = new PricingEntity();
        pricingEntity.setId(1L);
        pricingEntity.setAmount(AMOUNT);
        pricingEntity.setCountry(COUNTRY);
        return Collections.singletonList(pricingEntity);
    }

    private PricingDto createPricingDto() {
        PricingDto pricingDto = new PricingDto();
        pricingDto.setAmount(AMOUNT);
        pricingDto.setCountry(COUNTRY);
        return pricingDto;
    }
}
