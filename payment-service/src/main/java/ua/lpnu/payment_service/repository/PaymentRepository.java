package ua.lpnu.payment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.lpnu.payment_service.model.Payment;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUserId(Long userId);
    List<Payment> findByLotId(Long lotId);
}