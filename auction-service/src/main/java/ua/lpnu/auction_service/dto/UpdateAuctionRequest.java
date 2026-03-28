package ua.lpnu.auction_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ua.lpnu.auction_service.model.AuctionStatus;

import java.time.Instant;

public class UpdateAuctionRequest {

    @NotBlank(message = "title is required")
    private String title;

    @NotNull(message = "status is required")
    private AuctionStatus status;

    @NotNull(message = "startAt is required")
    private Instant startAt;

    @NotNull(message = "endAt is required")
    private Instant endAt;

    @NotNull(message = "sellerId is required")
    private Long sellerId;

    public UpdateAuctionRequest() {
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
}