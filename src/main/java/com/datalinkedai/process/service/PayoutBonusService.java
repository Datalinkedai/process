package com.datalinkedai.process.service;

import com.datalinkedai.process.domain.PayoutBonus;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PayoutBonus}.
 */
public interface PayoutBonusService {
    /**
     * Save a payoutBonus.
     *
     * @param payoutBonus the entity to save.
     * @return the persisted entity.
     */
    PayoutBonus save(PayoutBonus payoutBonus);

    /**
     * Updates a payoutBonus.
     *
     * @param payoutBonus the entity to update.
     * @return the persisted entity.
     */
    PayoutBonus update(PayoutBonus payoutBonus);

    /**
     * Partially updates a payoutBonus.
     *
     * @param payoutBonus the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PayoutBonus> partialUpdate(PayoutBonus payoutBonus);

    /**
     * Get all the payoutBonuses.
     *
     * @return the list of entities.
     */
    List<PayoutBonus> findAll();

    /**
     * Get the "id" payoutBonus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PayoutBonus> findOne(String id);

    /**
     * Delete the "id" payoutBonus.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
