package com.datalinkedai.process.service;

import com.datalinkedai.process.domain.Qualtiy;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Qualtiy}.
 */
public interface QualtiyService {
    /**
     * Save a qualtiy.
     *
     * @param qualtiy the entity to save.
     * @return the persisted entity.
     */
    Qualtiy save(Qualtiy qualtiy);

    /**
     * Updates a qualtiy.
     *
     * @param qualtiy the entity to update.
     * @return the persisted entity.
     */
    Qualtiy update(Qualtiy qualtiy);

    /**
     * Partially updates a qualtiy.
     *
     * @param qualtiy the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Qualtiy> partialUpdate(Qualtiy qualtiy);

    /**
     * Get all the qualtiys.
     *
     * @return the list of entities.
     */
    List<Qualtiy> findAll();

    /**
     * Get the "id" qualtiy.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Qualtiy> findOne(String id);

    /**
     * Delete the "id" qualtiy.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
