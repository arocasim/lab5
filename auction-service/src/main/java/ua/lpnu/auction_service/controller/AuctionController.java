package ua.lpnu.auction_service.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lpnu.auction_service.dto.CreateAuctionRequest;
import ua.lpnu.auction_service.dto.UpdateAuctionRequest;
import ua.lpnu.auction_service.model.Auction;
import ua.lpnu.auction_service.service.AuctionService;

import java.util.List;

@RestController
@RequestMapping("/api/auctions")
public class AuctionController {

    private final AuctionService service;

    public AuctionController(AuctionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Auction> create(@Valid @RequestBody CreateAuctionRequest req) {
        return ResponseEntity.status(201).body(service.create(req));
    }

    @GetMapping
    public List<Auction> getAll(@RequestParam(required = false) String sortBy) {
        return service.getAll(sortBy);
    }

    @GetMapping("/{id}")
    public Auction getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Auction update(@PathVariable Long id, @Valid @RequestBody UpdateAuctionRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/close")
    public Auction close(@PathVariable Long id) {
        return service.closeAuction(id);
    }
}