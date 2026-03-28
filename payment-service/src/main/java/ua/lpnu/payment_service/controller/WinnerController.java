package ua.lpnu.payment_service.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lpnu.payment_service.dto.CreateWinnerRequest;
import ua.lpnu.payment_service.dto.UpdateWinnerRequest;
import ua.lpnu.payment_service.model.Winner;
import ua.lpnu.payment_service.service.WinnerService;

import java.util.List;

@RestController
@RequestMapping("/api/winners")
public class WinnerController {

    private final WinnerService service;

    public WinnerController(WinnerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Winner> create(@Valid @RequestBody CreateWinnerRequest request) {
        return ResponseEntity.status(201).body(service.create(request));
    }

    @GetMapping
    public List<Winner> getAll(@RequestParam(required = false) Long userId) {
        return service.getAll(userId);
    }

    @GetMapping("/{id}")
    public Winner getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Winner update(@PathVariable Long id, @Valid @RequestBody UpdateWinnerRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}