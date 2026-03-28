package ua.lpnu.auction_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class UpdateBidRequest {

    @NotNull(message = "lotId is required")
    private Long lotId;

    @NotNull(message = "userId is required")
    private Long userId;

    @NotNull(message = "amount is required")
    @Positive(message = "amount must be positive")
    private BigDecimal amount;

    public UpdateBidRequest() {
    }

    public Long getLotId() {
        return lotId;
    }

    public Long getUserId() {
        return userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}