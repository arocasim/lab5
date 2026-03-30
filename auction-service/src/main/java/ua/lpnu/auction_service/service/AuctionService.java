package ua.lpnu.auction_service.service;

import feign.FeignException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.lpnu.auction_service.client.BidClient;
import ua.lpnu.auction_service.client.PaymentClient;
import ua.lpnu.auction_service.client.UserClient;
import ua.lpnu.auction_service.dto.BidResponse;
import ua.lpnu.auction_service.dto.CloseLotRequest;
import ua.lpnu.auction_service.dto.CreateAuctionRequest;
import ua.lpnu.auction_service.dto.UpdateAuctionRequest;
import ua.lpnu.auction_service.exception.BadRequestException;
import ua.lpnu.auction_service.exception.NotFoundException;
import ua.lpnu.auction_service.model.Auction;
import ua.lpnu.auction_service.model.AuctionStatus;
import ua.lpnu.auction_service.model.Lot;
import ua.lpnu.auction_service.repository.AuctionRepository;
import ua.lpnu.auction_service.repository.LotRepository;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuctionService {

    private static final Logger log = LoggerFactory.getLogger(AuctionService.class);

    private final AuctionRepository auctionRepo;
    private final LotRepository lotRepo;
    private final UserClient userClient;
    private final PaymentClient paymentClient;
    private final BidClient bidClient;

    public AuctionService(AuctionRepository auctionRepo, LotRepository lotRepo,
                          UserClient userClient, PaymentClient paymentClient,
                          BidClient bidClient) {
        this.auctionRepo = auctionRepo;
        this.lotRepo = lotRepo;
        this.userClient = userClient;
        this.paymentClient = paymentClient;
        this.bidClient = bidClient;
    }

    @Transactional
    public Auction create(CreateAuctionRequest req) {
        if (req.getEndAt().isBefore(req.getStartAt())) {
            throw new BadRequestException("endAt must be after startAt");
        }

        try {
            userClient.getUserById(req.getSellerId());
        } catch (FeignException.NotFound e) {
            throw new NotFoundException("Seller not found in user-service");
        }

        Auction auction = new Auction(
                null,
                req.getTitle(),
                AuctionStatus.DRAFT,
                req.getStartAt(),
                req.getEndAt(),
                req.getSellerId()
        );

        return auctionRepo.save(auction);
    }

    public List<Auction> getAll(String sortBy) {
        if (sortBy == null || sortBy.isBlank()) {
            return auctionRepo.findAll();
        }

        if (!sortBy.equals("title")
                && !sortBy.equals("startAt")
                && !sortBy.equals("endAt")
                && !sortBy.equals("status")) {
            throw new BadRequestException("Unsupported sort field: " + sortBy);
        }

        return auctionRepo.findAll(Sort.by(sortBy).ascending());
    }

    public Auction getById(Long id) {
        return auctionRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Auction not found"));
    }

    @Transactional
    public Auction update(Long id, UpdateAuctionRequest req) {
        if (req.getEndAt().isBefore(req.getStartAt())) {
            throw new BadRequestException("endAt must be after startAt");
        }

        Auction auction = auctionRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Auction not found"));

        try {
            userClient.getUserById(req.getSellerId());
        } catch (FeignException.NotFound e) {
            throw new NotFoundException("Seller not found in user-service");
        }

        auction.setTitle(req.getTitle());
        auction.setStatus(req.getStatus());
        auction.setStartAt(req.getStartAt());
        auction.setEndAt(req.getEndAt());
        auction.setSellerId(req.getSellerId());

        return auctionRepo.save(auction);
    }

    @Transactional
    public void delete(Long id) {
        Auction auction = auctionRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Auction not found"));

        auctionRepo.delete(auction);
    }

    @Transactional
    public void closeExpiredAuctions() {
        List<Auction> expired = auctionRepo.findByStatusAndEndAtBefore(
                AuctionStatus.ACTIVE, Instant.now());

        for (Auction auction : expired) {
            closeAuction(auction);
        }
    }

    @Transactional
    public Auction closeAuction(Long id) {
        Auction auction = auctionRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Auction not found"));

        if (!auction.isActive()) {
            throw new BadRequestException("Auction is not active");
        }

        closeAuction(auction);
        return auction;
    }

    private void closeAuction(Auction auction) {
        auction.close();
        auctionRepo.save(auction);

        List<Lot> lots = lotRepo.findByAuctionId(auction.getId());

        for (Lot lot : lots) {
            if (lot.isOpen()) {
                lot.close();
                lotRepo.save(lot);
                processLotClosure(lot);
            }
        }

        log.info("Auction {} closed successfully", auction.getId());
    }

    private void processLotClosure(Lot lot) {
        List<BidResponse> bids;
        try {
            bids = bidClient.getBidsByLotId(lot.getId());
        } catch (Exception e) {
            log.error("Failed to fetch bids for lot {}: {}", lot.getId(), e.getMessage());
            return;
        }

        if (bids.isEmpty()) {
            log.info("Lot {} has no bids, skipping winner determination", lot.getId());
            return;
        }

        Optional<BidResponse> winningBid = bids.stream()
                .max(Comparator.comparing(BidResponse::getAmount));

        if (winningBid.isEmpty()) {
            return;
        }

        BidResponse winner = winningBid.get();

        List<CloseLotRequest.LosingBidInfo> losingBids = bids.stream()
                .filter(b -> !b.getId().equals(winner.getId()))
                .map(b -> new CloseLotRequest.LosingBidInfo(b.getBidderId(), b.getAmount()))
                .collect(Collectors.toList());

        CloseLotRequest request = new CloseLotRequest(
                lot.getId(),
                winner.getId(),
                winner.getBidderId(),
                winner.getAmount(),
                losingBids
        );

        try {
            paymentClient.closeLot(request);
            log.info("Lot {} winner determined: bidder {}, amount {}",
                    lot.getId(), winner.getBidderId(), winner.getAmount());
        } catch (Exception e) {
            log.error("Failed to process payment closure for lot {}: {}",
                    lot.getId(), e.getMessage());
        }
    }
}
