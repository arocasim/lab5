package ua.lpnu.bid_service.dto;

import java.math.BigDecimal;

public class LotResponse {

    private Long id;
    private String title;
    private String description;
    private BigDecimal startPrice;
    private BigDecimal minStep;
    private String status;
    private AuctionInfo auction;

    public LotResponse() {}

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public BigDecimal getStartPrice() { return startPrice; }
    public BigDecimal getMinStep() { return minStep; }
    public String getStatus() { return status; }
    public AuctionInfo getAuction() { return auction; }

    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setStartPrice(BigDecimal startPrice) { this.startPrice = startPrice; }
    public void setMinStep(BigDecimal minStep) { this.minStep = minStep; }
    public void setStatus(String status) { this.status = status; }
    public void setAuction(AuctionInfo auction) { this.auction = auction; }

    public boolean isOpen() {
        return "OPEN".equals(status);
    }

    public static class AuctionInfo {
        private Long id;
        private String status;
        private Long sellerId;

        public AuctionInfo() {}

        public Long getId() { return id; }
        public String getStatus() { return status; }
        public Long getSellerId() { return sellerId; }

        public void setId(Long id) { this.id = id; }
        public void setStatus(String status) { this.status = status; }
        public void setSellerId(Long sellerId) { this.sellerId = sellerId; }

        public boolean isActive() {
            return "ACTIVE".equals(status);
        }
    }
}
