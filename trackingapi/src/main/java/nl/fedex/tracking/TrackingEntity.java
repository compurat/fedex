package nl.fedex.tracking;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "tracking")
class TrackingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "tracking_id")
    private String trackingId;

    @Column(name = "tracking_type")
    private String trackingTypes;

    Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    String getTrackingId() {
        return trackingId;
    }

    void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    String getTrackingTypes() {
        return trackingTypes;
    }

    void setTrackingTypes(String trackingTypes) {
        this.trackingTypes = trackingTypes;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackingEntity that = (TrackingEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(trackingId, that.trackingId) && Objects.equals(trackingTypes, that.trackingTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trackingId, trackingTypes);
    }
}
