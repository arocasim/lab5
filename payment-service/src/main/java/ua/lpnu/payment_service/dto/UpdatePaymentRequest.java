package ua.lpnu.payment_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ua.lpnu.payment_service.model.PaymentStatus;
import ua.lpnu.payment_service.model.PaymentType;

import java.math.BigDecimal;

public class UpdatePaymentRequest {

    @NotNull(message = "userId is required")
    private Long userId;

    @NotNull(message = "lotId is required")
    private Long lotId;

    @NotNull(message = "amount is required")
    @Positive(message = "amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "type is required")
    private PaymentType type;

    @NotNull(message = "status is required")
    private PaymentStatus status;

    public UpdatePaymentRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public Long getLotId() {
        return lotId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PaymentType getType() {
        return type;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}