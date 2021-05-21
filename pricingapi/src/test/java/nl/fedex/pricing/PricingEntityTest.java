package nl.fedex.pricing;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

import static org.junit.jupiter.api.Assertions.*;

class PricingEntityTest {
    @Test
    public void tesPricingEntity() {
        BeanTester beanTester = new BeanTester();
        beanTester.testBean(PricingEntity.class);
    }
}
