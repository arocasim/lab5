package ua.lpnu.bid_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.lpnu.bid_service.dto.LotResponse;

@FeignClient(name = "auction-service", url = "${services.auction.url}")
public interface AuctionClient {

    @GetMapping("/api/lots/{id}")
    LotResponse getLotById(@PathVariable Long id);
}
