package com.datalinkedai.process.service;

import com.datalinkedai.process.domain.Payout;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Payout}.
 */
public interface PayoutService {
    /**
     * Save a payout.
     *
     * @param payout the entity to save.
     * @return the persisted entity.
     */
    Payout save(Payout payout);

    /**
     * Updates a payout.
     *
     * @param payout the entity to update.
     * @return the persisted entity.
     */
    Payout update(Payout payout);

    /**
     * Partially updates a payout.
     *
     * @param payout the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Payout> partialUpdate(Payout payout);

    /**
     * Get all the payouts.
     *
     * @return the list of entities.
     */
    List<Payout> findAll();

    /**
     * Get the "id" payout.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Payout> findOne(String id);

    /**
     * Delete the "id" payout.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
