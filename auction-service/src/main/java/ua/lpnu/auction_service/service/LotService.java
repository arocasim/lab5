package ua.lpnu.auction_service.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ua.lpnu.auction_service.dto.CreateLotRequest;
import ua.lpnu.auction_service.dto.UpdateLotRequest;
import ua.lpnu.auction_service.exception.NotFoundException;
import ua.lpnu.auction_service.model.Auction;
import ua.lpnu.auction_service.model.Lot;
import ua.lpnu.auction_service.model.LotStatus;
import ua.lpnu.auction_service.repository.AuctionRepository;
import ua.lpnu.auction_service.repository.LotRepository;

import java.util.List;

@Service
public class LotService {

    private final LotRepository lotRepo;
    private final AuctionRepository auctionRepo;

    public LotService(LotRepository lotRepo, AuctionRepository auctionRepo) {
        this.lotRepo = lotRepo;
        this.auctionRepo = auctionRepo;
    }

    @Transactional
    public Lot create(CreateLotRequest req) {
        Auction auction = auctionRepo.findById(req.getAuctionId())
                .orElseThrow(() -> new NotFoundException("Auction not found"));

        Lot lot = new Lot(
                null,
                req.getTitle(),
                req.getDescription(),
                req.getStartPrice(),
                req.getMinStep(),
                LotStatus.OPEN,
                auction
        );

        return lotRepo.save(lot);
    }

    public List<Lot> getAll() {
        return lotRepo.findAll();
    }

    public Lot getById(Long id) {
        return lotRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Lot not found"));
    }

    public List<Lot> getByAuctionId(Long auctionId) {
        return lotRepo.findByAuctionId(auctionId);
    }

    @Transactional
    public Lot update(Long id, UpdateLotRequest req) {
        Lot lot = lotRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Lot not found"));

        Auction auction = auctionRepo.findById(req.getAuctionId())
                .orElseThrow(() -> new NotFoundException("Auction not found"));

        lot.setTitle(req.getTitle());
        lot.setDescription(req.getDescription());
        lot.setStartPrice(req.getStartPrice());
        lot.setMinStep(req.getMinStep());
        lot.setStatus(req.getStatus());
        lot.setAuction(auction);

        return lotRepo.save(lot);
    }

    @Transactional
    public void delete(Long id) {
        if (!lotRepo.existsById(id)) {
            throw new NotFoundException("Lot not found");
        }

        lotRepo.deleteById(id);
    }
}