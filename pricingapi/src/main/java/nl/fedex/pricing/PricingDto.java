package nl.fedex.pricing;


class PricingDto {
    private Double amount;
    private String country;
    Double getAmount() {
        return amount;
    }

    void setAmount(Double amount) {
        this.amount = amount;
    }

    String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
