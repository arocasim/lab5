package ua.lpnu.payment_service.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentType type;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private Instant createdAt;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long lotId;

    public Payment() {
    }

    public Payment(Long id, BigDecimal amount, PaymentType type, PaymentStatus status,
                   Instant createdAt, Long userId, Long lotId) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.status = status;
        this.createdAt = createdAt;
        this.userId = userId;
        this.lotId = lotId;
    }

    public Long getId() {
        return id;
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Long getUserId() {
        return userId;
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

    public void setType(PaymentType type) {
        this.type = type;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }

    public void markSuccess() {
        this.status = PaymentStatus.SUCCESS;
    }

    public void markFailed() {
        this.status = PaymentStatus.FAILED;
    }
}