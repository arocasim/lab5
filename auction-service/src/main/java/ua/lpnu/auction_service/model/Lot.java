package ua.lpnu.auction_service.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "lots")
public class Lot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String description;

    private BigDecimal startPrice;

    private BigDecimal minStep;

    @Enumerated(EnumType.STRING)
    private LotStatus status;

    @ManyToOne
    @JoinColumn(name = "auction_id", nullable = false)
    private Auction auction;

    public Lot() {
    }

    public Lot(Long id, String title, String description, BigDecimal startPrice, BigDecimal minStep, LotStatus status, Auction auction) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startPrice = startPrice;
        this.minStep = minStep;
        this.status = status;
        this.auction = auction;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public BigDecimal getMinStep() {
        return minStep;
    }

    public LotStatus getStatus() {
        return status;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public void setMinStep(BigDecimal minStep) {
        this.minStep = minStep;
    }

    public void setStatus(LotStatus status) {
        this.status = status;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public void open() {
        this.status = LotStatus.OPEN;
    }

    public void close() {
        this.status = LotStatus.CLOSED;
    }

    public boolean isOpen() {
        return this.status == LotStatus.OPEN;
    }
}