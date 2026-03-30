package ua.lpnu.auction_service.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class BidResponse {

    private Long id;
    private BigDecimal amount;
    private Instant createdAt;
    private Long bidderId;
    private Long lotId;

    public BidResponse() {
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Long getBidderId() {
        return bidderId;
    }

    public Long getLotId() {
        return lotId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setBidderId(Long bidderId) {
        this.bidderId = bidderId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }
}
