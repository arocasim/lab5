package ua.lpnu.payment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.lpnu.payment_service.model.Winner;

import java.util.List;
import java.util.Optional;

public interface WinnerRepository extends JpaRepository<Winner, Long> {
    List<Winner> findByUserId(Long userId);
    Optional<Winner> findByLotId(Long lotId);
}