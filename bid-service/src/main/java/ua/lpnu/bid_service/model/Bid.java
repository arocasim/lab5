package ua.lpnu.bid_service.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "bids")
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private Instant createdAt;

    @Column(nullable = false)
    private Long bidderId;

    @Column(nullable = false)
    private Long lotId;

    public Bid() {
    }

    public Bid(Long id, BigDecimal amount, Instant createdAt, Long bidderId, Long lotId) {
        this.id = id;
        this.amount = amount;
        this.createdAt = createdAt;
        this.bidderId = bidderId;
        this.lotId = lotId;
    }

    public Long getId() { return id; }
    public BigDecimal getAmount() { return amount; }
    public Instant getCreatedAt() { return createdAt; }
    public Long getBidderId() { return bidderId; }
    public Long getLotId() { return lotId; }

    public void setId(Long id) { this.id = id; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public void setBidderId(Long bidderId) { this.bidderId = bidderId; }
    public void setLotId(Long lotId) { this.lotId = lotId; }
}
