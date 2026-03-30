package ua.lpnu.auction_service.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.lpnu.auction_service.service.AuctionService;

@Component
public class AuctionScheduler {

    private static final Logger log = LoggerFactory.getLogger(AuctionScheduler.class);

    private final AuctionService auctionService;

    public AuctionScheduler(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @Scheduled(fixedRateString = "${auction.scheduler.interval:60000}")
    public void closeExpiredAuctions() {
        log.debug("Checking for expired auctions...");
        auctionService.closeExpiredAuctions();
    }
}
