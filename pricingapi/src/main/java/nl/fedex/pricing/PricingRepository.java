package nl.fedex.pricing;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PricingRepository extends CrudRepository<PricingEntity, Long> {
    List<PricingEntity> findByCountryInOrderByCountryDesc(final List<String> country);
}
