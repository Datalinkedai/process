package com.datalinkedai.process.service.impl;

import com.datalinkedai.process.domain.Payout;
import com.datalinkedai.process.repository.PayoutRepository;
import com.datalinkedai.process.service.PayoutService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Payout}.
 */
@Service
public class PayoutServiceImpl implements PayoutService {

    private final Logger log = LoggerFactory.getLogger(PayoutServiceImpl.class);

    private final PayoutRepository payoutRepository;

    public PayoutServiceImpl(PayoutRepository payoutRepository) {
        this.payoutRepository = payoutRepository;
    }

    @Override
    public Payout save(Payout payout) {
        log.debug("Request to save Payout : {}", payout);
        return payoutRepository.save(payout);
    }

    @Override
    public Payout update(Payout payout) {
        log.debug("Request to save Payout : {}", payout);
        return payoutRepository.save(payout);
    }

    @Override
    public Optional<Payout> partialUpdate(Payout payout) {
        log.debug("Request to partially update Payout : {}", payout);

        return payoutRepository
            .findById(payout.getId())
            .map(existingPayout -> {
                if (payout.getTotalDuration() != null) {
                    existingPayout.setTotalDuration(payout.getTotalDuration());
                }
                if (payout.getCostPerDuration() != null) {
                    existingPayout.setCostPerDuration(payout.getCostPerDuration());
                }
                if (payout.getTotalCost() != null) {
                    existingPayout.setTotalCost(payout.getTotalCost());
                }
                if (payout.getBonusPayout() != null) {
                    existingPayout.setBonusPayout(payout.getBonusPayout());
                }
                if (payout.getCostPerDurationBonus() != null) {
                    existingPayout.setCostPerDurationBonus(payout.getCostPerDurationBonus());
                }
                if (payout.getTotalDurationBonus() != null) {
                    existingPayout.setTotalDurationBonus(payout.getTotalDurationBonus());
                }
                if (payout.getTotalCostBonus() != null) {
                    existingPayout.setTotalCostBonus(payout.getTotalCostBonus());
                }
                if (payout.getPfAmount() != null) {
                    existingPayout.setPfAmount(payout.getPfAmount());
                }
                if (payout.getEsiAmount() != null) {
                    existingPayout.setEsiAmount(payout.getEsiAmount());
                }

                return existingPayout;
            })
            .map(payoutRepository::save);
    }

    @Override
    public List<Payout> findAll() {
        log.debug("Request to get all Payouts");
        return payoutRepository.findAll();
    }

    @Override
    public Optional<Payout> findOne(String id) {
        log.debug("Request to get Payout : {}", id);
        return payoutRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Payout : {}", id);
        payoutRepository.deleteById(id);
    }
}
