package nl.fedex.tracking;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

class TrackingEntityTest {

    @Test
    public void testShipmentEntity() {
        BeanTester beanTester = new BeanTester();
        beanTester.testBean(TrackingEntity.class);
    }
}
