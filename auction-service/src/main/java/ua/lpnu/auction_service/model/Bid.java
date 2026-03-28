package ua.lpnu.auction_service.model;

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

    @ManyToOne
    @JoinColumn(name = "lot_id", nullable = false)
    private Lot lot;

    public Bid() {
    }

    public Bid(Long id, BigDecimal amount, Instant createdAt, Long bidderId, Lot lot) {
        this.id = id;
        this.amount = amount;
        this.createdAt = createdAt;
        this.bidderId = bidderId;
        this.lot = lot;
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

    public Lot getLot() {
        return lot;
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

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    public boolean isHigherThan(Bid other) {
        if (other == null) return true;
        return this.amount.compareTo(other.amount) > 0;
    }
}