package nl.fedex.tracking;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrackingRepository extends CrudRepository<TrackingEntity, Long> {
    List<TrackingEntity> findByTrackingIdIn(final List<String> trackingId);
}
