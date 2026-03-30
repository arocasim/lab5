package ua.lpnu.bid_service.service;

import feign.FeignException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ua.lpnu.bid_service.client.AuctionClient;
import ua.lpnu.bid_service.client.UserClient;
import ua.lpnu.bid_service.dto.CreateBidRequest;
import ua.lpnu.bid_service.dto.LotResponse;
import ua.lpnu.bid_service.dto.UpdateBidRequest;
import ua.lpnu.bid_service.exception.BadRequestException;
import ua.lpnu.bid_service.exception.NotFoundException;
import ua.lpnu.bid_service.model.Bid;
import ua.lpnu.bid_service.repository.BidRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class BidService {

    private final BidRepository bidRepo;
    private final AuctionClient auctionClient;
    private final UserClient userClient;

    public BidService(BidRepository bidRepo, AuctionClient auctionClient, UserClient userClient) {
        this.bidRepo = bidRepo;
        this.auctionClient = auctionClient;
        this.userClient = userClient;
    }

    @Transactional
    public Bid create(CreateBidRequest req) {
        LotResponse lot = fetchLot(req.getLotId());

        if (!lot.isOpen()) {
            throw new BadRequestException("Lot is closed, bids are no longer accepted");
        }

        if (lot.getAuction() == null || !lot.getAuction().isActive()) {
            throw new BadRequestException("Auction is not active");
        }

        if (req.getUserId().equals(lot.getAuction().getSellerId())) {
            throw new BadRequestException("Seller cannot bid on their own auction");
        }

        try {
            userClient.getUserById(req.getUserId());
        } catch (FeignException.NotFound e) {
            throw new NotFoundException("Bidder not found in user-service");
        }

        BigDecimal minAllowed = calculateMinAllowed(req.getLotId(), lot);

        if (req.getAmount().compareTo(minAllowed) < 0) {
            throw new BadRequestException("Bid must be at least " + minAllowed);
        }

        Bid bid = new Bid();
        bid.setAmount(req.getAmount());
        bid.setCreatedAt(Instant.now());
        bid.setBidderId(req.getUserId());
        bid.setLotId(req.getLotId());

        return bidRepo.save(bid);
    }

    public List<Bid> getAll() {
        return bidRepo.findAll();
    }

    public List<Bid> getByLotId(Long lotId) {
        return bidRepo.findByLotId(lotId);
    }

    public Bid getById(Long id) {
        return bidRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Bid not found"));
    }

    @Transactional
    public Bid update(Long id, UpdateBidRequest req) {
        Bid bid = bidRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Bid not found"));

        LotResponse lot = fetchLot(req.getLotId());

        try {
            userClient.getUserById(req.getUserId());
        } catch (FeignException.NotFound e) {
            throw new NotFoundException("Bidder not found in user-service");
        }

        BigDecimal minAllowed = calculateMinAllowedForUpdate(req.getLotId(), lot, id);

        if (req.getAmount().compareTo(minAllowed) < 0) {
            throw new BadRequestException("Bid must be at least " + minAllowed);
        }

        bid.setAmount(req.getAmount());
        bid.setBidderId(req.getUserId());
        bid.setLotId(req.getLotId());

        return bidRepo.save(bid);
    }

    @Transactional
    public void delete(Long id) {
        if (!bidRepo.existsById(id)) {
            throw new NotFoundException("Bid not found");
        }
        bidRepo.deleteById(id);
    }

    private LotResponse fetchLot(Long lotId) {
        try {
            return auctionClient.getLotById(lotId);
        } catch (FeignException.NotFound e) {
            throw new NotFoundException("Lot not found in auction-service");
        }
    }

    private BigDecimal calculateMinAllowed(Long lotId, LotResponse lot) {
        return bidRepo.findFirstByLotIdOrderByAmountDesc(lotId)
                .map(highest -> highest.getAmount().add(lot.getMinStep()))
                .orElse(lot.getStartPrice());
    }

    private BigDecimal calculateMinAllowedForUpdate(Long lotId, LotResponse lot, Long currentBidId) {
        List<Bid> bids = bidRepo.findByLotId(lotId);

        BigDecimal highestOther = bids.stream()
                .filter(b -> !b.getId().equals(currentBidId))
                .map(Bid::getAmount)
                .max(BigDecimal::compareTo)
                .orElse(null);

        if (highestOther == null) {
            return lot.getStartPrice();
        }

        return highestOther.add(lot.getMinStep());
    }
}
