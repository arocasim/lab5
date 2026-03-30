package ua.lpnu.auction_service.dto;

import java.math.BigDecimal;
import java.util.List;

public class CloseLotRequest {

    private Long lotId;
    private Long winningBidId;
    private Long winnerId;
    private BigDecimal winningAmount;
    private List<LosingBidInfo> losingBids;

    public CloseLotRequest() {
    }

    public CloseLotRequest(Long lotId, Long winningBidId, Long winnerId,
                           BigDecimal winningAmount, List<LosingBidInfo> losingBids) {
        this.lotId = lotId;
        this.winningBidId = winningBidId;
        this.winnerId = winnerId;
        this.winningAmount = winningAmount;
        this.losingBids = losingBids;
    }

    public Long getLotId() {
        return lotId;
    }

    public Long getWinningBidId() {
        return winningBidId;
    }

    public Long getWinnerId() {
        return winnerId;
    }

    public BigDecimal getWinningAmount() {
        return winningAmount;
    }

    public List<LosingBidInfo> getLosingBids() {
        return losingBids;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }

    public void setWinningBidId(Long winningBidId) {
        this.winningBidId = winningBidId;
    }

    public void setWinnerId(Long winnerId) {
        this.winnerId = winnerId;
    }

    public void setWinningAmount(BigDecimal winningAmount) {
        this.winningAmount = winningAmount;
    }

    public void setLosingBids(List<LosingBidInfo> losingBids) {
        this.losingBids = losingBids;
    }

    public static class LosingBidInfo {
        private Long bidderId;
        private BigDecimal amount;

        public LosingBidInfo() {
        }

        public LosingBidInfo(Long bidderId, BigDecimal amount) {
            this.bidderId = bidderId;
            this.amount = amount;
        }

        public Long getBidderId() {
            return bidderId;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setBidderId(Long bidderId) {
            this.bidderId = bidderId;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }
    }
}
