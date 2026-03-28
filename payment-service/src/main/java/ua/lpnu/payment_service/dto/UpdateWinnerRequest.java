package ua.lpnu.payment_service.dto;

import jakarta.validation.constraints.NotNull;

public class UpdateWinnerRequest {

    @NotNull(message = "lotId is required")
    private Long lotId;

    @NotNull(message = "winningBidId is required")
    private Long winningBidId;

    public UpdateWinnerRequest() {
    }

    public Long getLotId() {
        return lotId;
    }

    public Long getWinningBidId() {
        return winningBidId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }

    public void setWinningBidId(Long winningBidId) {
        this.winningBidId = winningBidId;
    }
}