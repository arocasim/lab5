package ua.lpnu.payment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.lpnu.payment_service.dto.BidResponse;
import ua.lpnu.payment_service.dto.LotResponse;

@FeignClient(name = "auction-service", url = "${services.auction.url}")
public interface AuctionClient {

    @GetMapping("/api/lots/{id}")
    LotResponse getLotById(@PathVariable Long id);

    @GetMapping("/api/bids/{id}")
    BidResponse getBidById(@PathVariable Long id);
}