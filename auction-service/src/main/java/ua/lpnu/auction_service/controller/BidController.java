package ua.lpnu.auction_service.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lpnu.auction_service.dto.CreateBidRequest;
import ua.lpnu.auction_service.dto.UpdateBidRequest;
import ua.lpnu.auction_service.model.Bid;
import ua.lpnu.auction_service.service.BidService;

import java.util.List;

@RestController
@RequestMapping("/api/bids")
public class BidController {

    private final BidService service;

    public BidController(BidService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Bid> create(@Valid @RequestBody CreateBidRequest req) {
        return ResponseEntity.status(201).body(service.create(req));
    }

    @GetMapping
    public List<Bid> getAll(@RequestParam(required = false) Long lotId) {
        if (lotId != null) {
            return service.getByLotId(lotId);
        }
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Bid getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Bid update(@PathVariable Long id, @Valid @RequestBody UpdateBidRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}