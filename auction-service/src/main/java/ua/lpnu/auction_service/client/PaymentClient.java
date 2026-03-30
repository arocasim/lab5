package ua.lpnu.auction_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ua.lpnu.auction_service.dto.CloseLotRequest;

@FeignClient(name = "payment-service", url = "${services.payment.url}")
public interface PaymentClient {

    @PostMapping("/api/auction-closure/close-lot")
    void closeLot(@RequestBody CloseLotRequest request);
}
