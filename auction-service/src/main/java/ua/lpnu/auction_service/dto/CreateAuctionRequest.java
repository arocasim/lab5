package ua.lpnu.auction_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public class CreateAuctionRequest {

    @NotBlank(message = "title is required")
    private String title;

    @NotNull(message = "sellerId is required")
    private Long sellerId;

    @NotNull(message = "startAt is required")
    private Instant startAt;

    @NotNull(message = "endAt is required")
    private Instant endAt;

    public CreateAuctionRequest() {
    }

    public String getTitle() {
        return title;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public Instant getStartAt() {
        return startAt;
    }

    public Instant getEndAt() {
        return endAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public void setStartAt(Instant startAt) {
        this.startAt = startAt;
    }

    public void setEndAt(Instant endAt) {
        this.endAt = endAt;
    }
}