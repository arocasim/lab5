package ua.lpnu.auction_service.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "auctions")
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private AuctionStatus status;

    private Instant startAt;

    private Instant endAt;

    @Column(nullable = false)
    private Long sellerId;

    public Auction() {
    }

    public Auction(Long id, String title, AuctionStatus status, Instant startAt, Instant endAt, Long sellerId) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.startAt = startAt;
        this.endAt = endAt;
        this.sellerId = sellerId;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public AuctionStatus getStatus() {
        return status;
    }

    public Instant getStartAt() {
        return startAt;
    }

    public Instant getEndAt() {
        return endAt;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStatus(AuctionStatus status) {
        this.status = status;
    }

    public void setStartAt(Instant startAt) {
        this.startAt = startAt;
    }

    public void setEndAt(Instant endAt) {
        this.endAt = endAt;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public void activate() {
        this.status = AuctionStatus.ACTIVE;
    }

    public void close() {
        this.status = AuctionStatus.CLOSED;
    }

    public boolean isActive() {
        return this.status == AuctionStatus.ACTIVE;
    }
}