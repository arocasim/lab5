package ua.lpnu.auction_service.service;

import feign.FeignException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ua.lpnu.auction_service.client.UserClient;
import ua.lpnu.auction_service.dto.CreateBidRequest;
import ua.lpnu.auction_service.dto.UpdateBidRequest;
import ua.lpnu.auction_service.exception.BadRequestException;
import ua.lpnu.auction_service.exception.NotFoundException;
import ua.lpnu.auction_service.model.Bid;
import ua.lpnu.auction_service.model.Lot;
import ua.lpnu.auction_service.repository.BidRepository;
import ua.lpnu.auction_service.repository.LotRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class BidService {

    private final BidRepository bidRepo;
    private final LotRepository lotRepo;
    private final UserClient userClient;

    public BidService(BidRepository bidRepo, LotRepository lotRepo, UserClient userClient) {
        this.bidRepo = bidRepo;
        this.lotRepo = lotRepo;
        this.userClient = userClient;
    }

    @Transactional
    public Bid create(CreateBidRequest req) {
        Lot lot = lotRepo.findById(req.getLotId())
                .orElseThrow(() -> new NotFoundException("Lot not found"));

        try {
            userClient.getUserById(req.getUserId());
        } catch (FeignException.NotFound e) {
            throw new NotFoundException("Bidder not found in user-service");
        }

        BigDecimal minAllowed = calculateMinAllowed(lot);

        if (req.getAmount().compareTo(minAllowed) < 0) {
            throw new BadRequestException("Bid must be at least " + minAllowed);
        }

        Bid bid = new Bid();
        bid.setAmount(req.getAmount());
        bid.setCreatedAt(Instant.now());
        bid.setBidderId(req.getUserId());
        bid.setLot(lot);

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

        Lot lot = lotRepo.findById(req.getLotId())
                .orElseThrow(() -> new NotFoundException("Lot not found"));

        try {
            userClient.getUserById(req.getUserId());
        } catch (FeignException.NotFound e) {
            throw new NotFoundException("Bidder not found in user-service");
        }

        BigDecimal minAllowed = calculateMinAllowedForUpdate(lot, id);

        if (req.getAmount().compareTo(minAllowed) < 0) {
            throw new BadRequestException("Bid must be at least " + minAllowed);
        }

        bid.setAmount(req.getAmount());
        bid.setBidderId(req.getUserId());
        bid.setLot(lot);

        return bidRepo.save(bid);
    }

    @Transactional
    public void delete(Long id) {
        if (!bidRepo.existsById(id)) {
            throw new NotFoundException("Bid not found");
        }

        bidRepo.deleteById(id);
    }

    private BigDecimal calculateMinAllowed(Lot lot) {
        return bidRepo.findFirstByLotIdOrderByAmountDesc(lot.getId())
                .map(highest -> highest.getAmount().add(lot.getMinStep()))
                .orElse(lot.getStartPrice());
    }

    private BigDecimal calculateMinAllowedForUpdate(Lot lot, Long currentBidId) {
        List<Bid> bids = bidRepo.findByLotId(lot.getId());

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