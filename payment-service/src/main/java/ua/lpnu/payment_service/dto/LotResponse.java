package ua.lpnu.payment_service.dto;

import java.math.BigDecimal;

public class LotResponse {

    private Long id;
    private String title;
    private BigDecimal startPrice;
    private BigDecimal minStep;

    public LotResponse() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public BigDecimal getMinStep() {
        return minStep;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public void setMinStep(BigDecimal minStep) {
        this.minStep = minStep;
    }
}