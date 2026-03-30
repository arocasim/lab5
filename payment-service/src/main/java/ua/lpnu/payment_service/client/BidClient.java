package ua.lpnu.payment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ua.lpnu.payment_service.dto.BidResponse;

import java.util.List;

@FeignClient(name = "bid-service", url = "${services.bid.url}")
public interface BidClient {

    @GetMapping("/api/bids/{id}")
    BidResponse getBidById(@PathVariable Long id);

    @GetMapping("/api/bids")
    List<BidResponse> getBidsByLotId(@RequestParam("lotId") Long lotId);
}
