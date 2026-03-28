package ua.lpnu.auction_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class CreateLotRequest {

    @NotBlank(message = "title is required")
    private String title;

    private String description;

    @NotNull(message = "auctionId is required")
    private Long auctionId;

    @NotNull(message = "startPrice is required")
    @Positive(message = "startPrice must be positive")
    private BigDecimal startPrice;

    @NotNull(message = "minStep is required")
    @Positive(message = "minStep must be positive")
    private BigDecimal minStep;

    public CreateLotRequest() {
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public BigDecimal getMinStep() {
        return minStep;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }

    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public void setMinStep(BigDecimal minStep) {
        this.minStep = minStep;
    }
}