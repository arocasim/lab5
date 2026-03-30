package ua.lpnu.auction_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.lpnu.auction_service.model.Auction;
import ua.lpnu.auction_service.model.AuctionStatus;

import java.time.Instant;
import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    List<Auction> findByStatusAndEndAtBefore(AuctionStatus status, Instant time);
}