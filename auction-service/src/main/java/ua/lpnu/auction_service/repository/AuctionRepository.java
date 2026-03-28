package ua.lpnu.auction_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.lpnu.auction_service.model.Auction;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
}