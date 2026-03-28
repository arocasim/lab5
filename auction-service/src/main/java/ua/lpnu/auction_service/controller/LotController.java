package ua.lpnu.auction_service.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lpnu.auction_service.dto.CreateLotRequest;
import ua.lpnu.auction_service.dto.UpdateLotRequest;
import ua.lpnu.auction_service.model.Lot;
import ua.lpnu.auction_service.service.LotService;

import java.util.List;

@RestController
@RequestMapping("/api/lots")
public class LotController {

    private final LotService service;

    public LotController(LotService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Lot> create(@Valid @RequestBody CreateLotRequest req) {
        return ResponseEntity.status(201).body(service.create(req));
    }

    @GetMapping
    public List<Lot> getAll(@RequestParam(required = false) Long auctionId) {
        if (auctionId != null) {
            return service.getByAuctionId(auctionId);
        }
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Lot getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Lot update(@PathVariable Long id, @Valid @RequestBody UpdateLotRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}