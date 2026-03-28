package ua.lpnu.payment_service.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "winners")
public class Winner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long lotId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, unique = true)
    private Long winningBidId;

    private Instant decidedAt;

    public Winner() {
    }

    public Winner(Long id, Long lotId, Long userId, Long winningBidId, Instant decidedAt) {
        this.id = id;
        this.lotId = lotId;
        this.userId = userId;
        this.winningBidId = winningBidId;
        this.decidedAt = decidedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getLotId() {
        return lotId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getWinningBidId() {
        return winningBidId;
    }

    public Instant getDecidedAt() {
        return decidedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setWinningBidId(Long winningBidId) {
        this.winningBidId = winningBidId;
    }

    public void setDecidedAt(Instant decidedAt) {
        this.decidedAt = decidedAt;
    }
}