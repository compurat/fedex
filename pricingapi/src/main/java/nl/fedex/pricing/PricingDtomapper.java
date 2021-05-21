package nl.fedex.pricing;

import org.springframework.stereotype.Component;

@Component
public class PricingDtomapper {

    PricingDto mapper(final PricingEntity pricingEntity) {
        PricingDto pricingDto = new PricingDto();
        pricingDto.setAmount(pricingEntity.getAmount());
        pricingDto.setCountry(pricingEntity.getCountry());
        return pricingDto;
    }
}
