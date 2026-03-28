package ua.lpnu.payment_service.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lpnu.payment_service.dto.CreatePaymentRequest;
import ua.lpnu.payment_service.dto.UpdatePaymentRequest;
import ua.lpnu.payment_service.model.Payment;
import ua.lpnu.payment_service.service.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Payment> create(@Valid @RequestBody CreatePaymentRequest request) {
        return ResponseEntity.status(201).body(service.create(request));
    }

    @GetMapping
    public List<Payment> getAll(@RequestParam(required = false) Long userId,
                                @RequestParam(required = false) Long lotId) {
        return service.getAll(userId, lotId);
    }

    @GetMapping("/{id}")
    public Payment getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Payment update(@PathVariable Long id, @Valid @RequestBody UpdatePaymentRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}