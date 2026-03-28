package ua.lpnu.auction_service.service;

import feign.FeignException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.lpnu.auction_service.client.UserClient;
import ua.lpnu.auction_service.dto.CreateAuctionRequest;
import ua.lpnu.auction_service.dto.UpdateAuctionRequest;
import ua.lpnu.auction_service.exception.BadRequestException;
import ua.lpnu.auction_service.exception.NotFoundException;
import ua.lpnu.auction_service.model.Auction;
import ua.lpnu.auction_service.model.AuctionStatus;
import ua.lpnu.auction_service.repository.AuctionRepository;

import java.util.List;

@Service
public class AuctionService {

    private final AuctionRepository auctionRepo;
    private final UserClient userClient;

    public AuctionService(AuctionRepository auctionRepo, UserClient userClient) {
        this.auctionRepo = auctionRepo;
        this.userClient = userClient;
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
}