package nl.fedex.pricing;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "pricing")
class PricingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "country")
    private String country;

    Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    Double getAmount() {
        return amount;
    }

    void setAmount(Double amount) {
        this.amount = amount;
    }

    String getCountry() {
        return country;
    }

    void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PricingEntity that = (PricingEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(amount, that.amount) && Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, country);
    }
}
