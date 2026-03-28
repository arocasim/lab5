package ua.lpnu.payment_service.dto;

import java.math.BigDecimal;

public class BidResponse {

    private Long id;
    private BigDecimal amount;
    private Long bidderId;
    private LotResponse lot;

    public BidResponse() {
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Long getBidderId() {
        return bidderId;
    }

    public LotResponse getLot() {
        return lot;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setBidderId(Long bidderId) {
        this.bidderId = bidderId;
    }

    public void setLot(LotResponse lot) {
        this.lot = lot;
    }
}