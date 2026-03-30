package ua.lpnu.payment_service.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.lpnu.payment_service.dto.CloseLotRequest;
import ua.lpnu.payment_service.exception.BadRequestException;
import ua.lpnu.payment_service.model.Payment;
import ua.lpnu.payment_service.model.PaymentStatus;
import ua.lpnu.payment_service.model.PaymentType;
import ua.lpnu.payment_service.model.Winner;
import ua.lpnu.payment_service.repository.PaymentRepository;
import ua.lpnu.payment_service.repository.WinnerRepository;

import java.time.Instant;

@Service
public class AuctionClosureService {

    private static final Logger log = LoggerFactory.getLogger(AuctionClosureService.class);

    private final WinnerRepository winnerRepository;
    private final PaymentRepository paymentRepository;

    public AuctionClosureService(WinnerRepository winnerRepository,
                                  PaymentRepository paymentRepository) {
        this.winnerRepository = winnerRepository;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public void processLotClosure(CloseLotRequest request) {
        if (winnerRepository.findByLotId(request.getLotId()).isPresent()) {
            throw new BadRequestException("Winner for lot " + request.getLotId() + " already exists");
        }

        Winner winner = new Winner();
        winner.setLotId(request.getLotId());
        winner.setUserId(request.getWinnerId());
        winner.setWinningBidId(request.getWinningBidId());
        winner.setDecidedAt(Instant.now());
        winnerRepository.save(winner);

        Payment capturePayment = new Payment();
        capturePayment.setAmount(request.getWinningAmount());
        capturePayment.setType(PaymentType.CAPTURE);
        capturePayment.setStatus(PaymentStatus.SUCCESS);
        capturePayment.setCreatedAt(Instant.now());
        capturePayment.setUserId(request.getWinnerId());
        capturePayment.setLotId(request.getLotId());
        paymentRepository.save(capturePayment);

        log.info("Winner for lot {}: user {}, amount {}",
                request.getLotId(), request.getWinnerId(), request.getWinningAmount());

        if (request.getLosingBids() != null) {
            for (CloseLotRequest.LosingBidInfo loser : request.getLosingBids()) {
                Payment refundPayment = new Payment();
                refundPayment.setAmount(loser.getAmount());
                refundPayment.setType(PaymentType.REFUND);
                refundPayment.setStatus(PaymentStatus.SUCCESS);
                refundPayment.setCreatedAt(Instant.now());
                refundPayment.setUserId(loser.getBidderId());
                refundPayment.setLotId(request.getLotId());
                paymentRepository.save(refundPayment);

                log.info("Refund for lot {}: user {}, amount {}",
                        request.getLotId(), loser.getBidderId(), loser.getAmount());
            }
        }
    }
}
