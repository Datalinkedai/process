package com.datalinkedai.process.service;

import com.datalinkedai.process.domain.Candidate;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Candidate}.
 */
public interface CandidateService {
    /**
     * Save a candidate.
     *
     * @param candidate the entity to save.
     * @return the persisted entity.
     */
    Candidate save(Candidate candidate);

    /**
     * Updates a candidate.
     *
     * @param candidate the entity to update.
     * @return the persisted entity.
     */
    Candidate update(Candidate candidate);

    /**
     * Partially updates a candidate.
     *
     * @param candidate the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Candidate> partialUpdate(Candidate candidate);

    /**
     * Get all the candidates.
     *
     * @return the list of entities.
     */
    List<Candidate> findAll();

    /**
     * Get the "id" candidate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Candidate> findOne(String id);

    /**
     * Delete the "id" candidate.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
