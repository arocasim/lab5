package ua.lpnu.payment_service.service;

import feign.FeignException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ua.lpnu.payment_service.client.BidClient;
import ua.lpnu.payment_service.dto.BidResponse;
import ua.lpnu.payment_service.dto.CreateWinnerRequest;
import ua.lpnu.payment_service.dto.UpdateWinnerRequest;
import ua.lpnu.payment_service.exception.BadRequestException;
import ua.lpnu.payment_service.exception.NotFoundException;
import ua.lpnu.payment_service.model.Winner;
import ua.lpnu.payment_service.repository.WinnerRepository;

import java.time.Instant;
import java.util.List;

@Service
public class WinnerService {

    private final WinnerRepository winnerRepository;
    private final BidClient bidClient;

    public WinnerService(WinnerRepository winnerRepository, BidClient bidClient) {
        this.winnerRepository = winnerRepository;
        this.bidClient = bidClient;
    }

    @Transactional
    public Winner create(CreateWinnerRequest request) {
        BidResponse bid;

        try {
            bid = bidClient.getBidById(request.getWinningBidId());
        } catch (FeignException.NotFound e) {
            throw new NotFoundException("Bid not found in bid-service");
        }

        if (bid.getLotId() == null || !bid.getLotId().equals(request.getLotId())) {
            throw new BadRequestException("Bid does not belong to the specified lot");
        }

        if (winnerRepository.findByLotId(request.getLotId()).isPresent()) {
            throw new BadRequestException("Winner for this lot already exists");
        }

        Winner winner = new Winner();
        winner.setLotId(request.getLotId());
        winner.setUserId(bid.getBidderId());
        winner.setWinningBidId(request.getWinningBidId());
        winner.setDecidedAt(Instant.now());

        return winnerRepository.save(winner);
    }

    public List<Winner> getAll(Long userId) {
        if (userId != null) {
            return winnerRepository.findByUserId(userId);
        }
        return winnerRepository.findAll();
    }

    public Winner getById(Long id) {
        return winnerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Winner not found"));
    }

    @Transactional
    public Winner update(Long id, UpdateWinnerRequest request) {
        Winner winner = winnerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Winner not found"));

        BidResponse bid;

        try {
            bid = bidClient.getBidById(request.getWinningBidId());
        } catch (FeignException.NotFound e) {
            throw new NotFoundException("Bid not found in bid-service");
        }

        if (bid.getLotId() == null || !bid.getLotId().equals(request.getLotId())) {
            throw new BadRequestException("Bid does not belong to the specified lot");
        }

        winner.setLotId(request.getLotId());
        winner.setUserId(bid.getBidderId());
        winner.setWinningBidId(request.getWinningBidId());
        winner.setDecidedAt(Instant.now());

        return winnerRepository.save(winner);
    }

    @Transactional
    public void delete(Long id) {
        if (!winnerRepository.existsById(id)) {
            throw new NotFoundException("Winner not found");
        }
        winnerRepository.deleteById(id);
    }
}
