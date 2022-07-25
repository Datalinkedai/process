package com.datalinkedai.process.service.impl;

import com.datalinkedai.process.domain.PayoutBonus;
import com.datalinkedai.process.repository.PayoutBonusRepository;
import com.datalinkedai.process.service.PayoutBonusService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link PayoutBonus}.
 */
@Service
public class PayoutBonusServiceImpl implements PayoutBonusService {

    private final Logger log = LoggerFactory.getLogger(PayoutBonusServiceImpl.class);

    private final PayoutBonusRepository payoutBonusRepository;

    public PayoutBonusServiceImpl(PayoutBonusRepository payoutBonusRepository) {
        this.payoutBonusRepository = payoutBonusRepository;
    }

    @Override
    public PayoutBonus save(PayoutBonus payoutBonus) {
        log.debug("Request to save PayoutBonus : {}", payoutBonus);
        return payoutBonusRepository.save(payoutBonus);
    }

    @Override
    public PayoutBonus update(PayoutBonus payoutBonus) {
        log.debug("Request to save PayoutBonus : {}", payoutBonus);
        return payoutBonusRepository.save(payoutBonus);
    }

    @Override
    public Optional<PayoutBonus> partialUpdate(PayoutBonus payoutBonus) {
        log.debug("Request to partially update PayoutBonus : {}", payoutBonus);

        return payoutBonusRepository
            .findById(payoutBonus.getId())
            .map(existingPayoutBonus -> {
                if (payoutBonus.getBasicDuration() != null) {
                    existingPayoutBonus.setBasicDuration(payoutBonus.getBasicDuration());
                }
                if (payoutBonus.getBasicCost() != null) {
                    existingPayoutBonus.setBasicCost(payoutBonus.getBasicCost());
                }
                if (payoutBonus.getBonusDuration() != null) {
                    existingPayoutBonus.setBonusDuration(payoutBonus.getBonusDuration());
                }
                if (payoutBonus.getBonusCost() != null) {
                    existingPayoutBonus.setBonusCost(payoutBonus.getBonusCost());
                }

                return existingPayoutBonus;
            })
            .map(payoutBonusRepository::save);
    }

    @Override
    public List<PayoutBonus> findAll() {
        log.debug("Request to get all PayoutBonuses");
        return payoutBonusRepository.findAll();
    }

    @Override
    public Optional<PayoutBonus> findOne(String id) {
        log.debug("Request to get PayoutBonus : {}", id);
        return payoutBonusRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete PayoutBonus : {}", id);
        payoutBonusRepository.deleteById(id);
    }
}
