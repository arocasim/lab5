package ua.lpnu.payment_service.service;

import feign.FeignException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ua.lpnu.payment_service.client.AuctionClient;
import ua.lpnu.payment_service.client.UserClient;
import ua.lpnu.payment_service.dto.CreatePaymentRequest;
import ua.lpnu.payment_service.dto.UpdatePaymentRequest;
import ua.lpnu.payment_service.exception.NotFoundException;
import ua.lpnu.payment_service.model.Payment;
import ua.lpnu.payment_service.model.PaymentStatus;
import ua.lpnu.payment_service.repository.PaymentRepository;

import java.time.Instant;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository repository;
    private final UserClient userClient;
    private final AuctionClient auctionClient;

    public PaymentService(PaymentRepository repository, UserClient userClient, AuctionClient auctionClient) {
        this.repository = repository;
        this.userClient = userClient;
        this.auctionClient = auctionClient;
    }

    @Transactional
    public Payment create(CreatePaymentRequest request) {
        try {
            userClient.getUserById(request.getUserId());
        } catch (FeignException.NotFound e) {
            throw new NotFoundException("User not found in user-service");
        }

        try {
            auctionClient.getLotById(request.getLotId());
        } catch (FeignException.NotFound e) {
            throw new NotFoundException("Lot not found in auction-service");
        }

        Payment payment = new Payment();
        payment.setAmount(request.getAmount());
        payment.setType(request.getType());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setCreatedAt(Instant.now());
        payment.setUserId(request.getUserId());
        payment.setLotId(request.getLotId());

        return repository.save(payment);
    }

    public List<Payment> getAll(Long userId, Long lotId) {
        if (userId != null) {
            return repository.findByUserId(userId);
        }
        if (lotId != null) {
            return repository.findByLotId(lotId);
        }
        return repository.findAll();
    }

    public Payment getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found"));
    }

    @Transactional
    public Payment update(Long id, UpdatePaymentRequest request) {
        Payment payment = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found"));

        try {
            userClient.getUserById(request.getUserId());
        } catch (FeignException.NotFound e) {
            throw new NotFoundException("User not found in user-service");
        }

        try {
            auctionClient.getLotById(request.getLotId());
        } catch (FeignException.NotFound e) {
            throw new NotFoundException("Lot not found in auction-service");
        }

        payment.setAmount(request.getAmount());
        payment.setType(request.getType());
        payment.setStatus(request.getStatus());
        payment.setUserId(request.getUserId());
        payment.setLotId(request.getLotId());

        return repository.save(payment);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Payment not found");
        }
        repository.deleteById(id);
    }
}