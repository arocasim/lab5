package ua.lpnu.bid_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.lpnu.bid_service.model.Bid;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findByLotId(Long lotId);
    Optional<Bid> findFirstByLotIdOrderByAmountDesc(Long lotId);
}
