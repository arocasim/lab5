package ua.lpnu.payment_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.lpnu.payment_service.dto.CloseLotRequest;
import ua.lpnu.payment_service.service.AuctionClosureService;

@RestController
@RequestMapping("/api/auction-closure")
public class AuctionClosureController {

    private final AuctionClosureService service;

    public AuctionClosureController(AuctionClosureService service) {
        this.service = service;
    }

    @PostMapping("/close-lot")
    public ResponseEntity<Void> closeLot(@RequestBody CloseLotRequest request) {
        service.processLotClosure(request);
        return ResponseEntity.ok().build();
    }
}
