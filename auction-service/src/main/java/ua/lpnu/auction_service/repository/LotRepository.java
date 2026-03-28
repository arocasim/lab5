package ua.lpnu.auction_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.lpnu.auction_service.model.Lot;

import java.util.List;

public interface LotRepository extends JpaRepository<Lot, Long> {
    List<Lot> findByAuctionId(Long auctionId);
}