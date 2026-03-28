package ua.lpnu.auction_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ua.lpnu.auction_service.model.LotStatus;

import java.math.BigDecimal;

public class UpdateLotRequest {

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

    @NotNull(message = "status is required")
    private LotStatus status;

    public UpdateLotRequest() {
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

    public LotStatus getStatus() {
        return status;
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

    public void setStatus(LotStatus status) {
        this.status = status;
    }
}