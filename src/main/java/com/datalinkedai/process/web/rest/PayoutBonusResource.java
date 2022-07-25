package com.datalinkedai.process.web.rest;

import com.datalinkedai.process.domain.PayoutBonus;
import com.datalinkedai.process.repository.PayoutBonusRepository;
import com.datalinkedai.process.service.PayoutBonusService;
import com.datalinkedai.process.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.datalinkedai.process.domain.PayoutBonus}.
 */
@RestController
@RequestMapping("/api")
public class PayoutBonusResource {

    private final Logger log = LoggerFactory.getLogger(PayoutBonusResource.class);

    private static final String ENTITY_NAME = "processPayoutBonus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PayoutBonusService payoutBonusService;

    private final PayoutBonusRepository payoutBonusRepository;

    public PayoutBonusResource(PayoutBonusService payoutBonusService, PayoutBonusRepository payoutBonusRepository) {
        this.payoutBonusService = payoutBonusService;
        this.payoutBonusRepository = payoutBonusRepository;
    }

    /**
     * {@code POST  /payout-bonuses} : Create a new payoutBonus.
     *
     * @param payoutBonus the payoutBonus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new payoutBonus, or with status {@code 400 (Bad Request)} if the payoutBonus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payout-bonuses")
    public ResponseEntity<PayoutBonus> createPayoutBonus(@RequestBody PayoutBonus payoutBonus) throws URISyntaxException {
        log.debug("REST request to save PayoutBonus : {}", payoutBonus);
        if (payoutBonus.getId() != null) {
            throw new BadRequestAlertException("A new payoutBonus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PayoutBonus result = payoutBonusService.save(payoutBonus);
        return ResponseEntity
            .created(new URI("/api/payout-bonuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /payout-bonuses/:id} : Updates an existing payoutBonus.
     *
     * @param id the id of the payoutBonus to save.
     * @param payoutBonus the payoutBonus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payoutBonus,
     * or with status {@code 400 (Bad Request)} if the payoutBonus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the payoutBonus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payout-bonuses/{id}")
    public ResponseEntity<PayoutBonus> updatePayoutBonus(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody PayoutBonus payoutBonus
    ) throws URISyntaxException {
        log.debug("REST request to update PayoutBonus : {}, {}", id, payoutBonus);
        if (payoutBonus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, payoutBonus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!payoutBonusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PayoutBonus result = payoutBonusService.update(payoutBonus);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payoutBonus.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /payout-bonuses/:id} : Partial updates given fields of an existing payoutBonus, field will ignore if it is null
     *
     * @param id the id of the payoutBonus to save.
     * @param payoutBonus the payoutBonus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payoutBonus,
     * or with status {@code 400 (Bad Request)} if the payoutBonus is not valid,
     * or with status {@code 404 (Not Found)} if the payoutBonus is not found,
     * or with status {@code 500 (Internal Server Error)} if the payoutBonus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payout-bonuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PayoutBonus> partialUpdatePayoutBonus(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody PayoutBonus payoutBonus
    ) throws URISyntaxException {
        log.debug("REST request to partial update PayoutBonus partially : {}, {}", id, payoutBonus);
        if (payoutBonus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, payoutBonus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!payoutBonusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PayoutBonus> result = payoutBonusService.partialUpdate(payoutBonus);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payoutBonus.getId())
        );
    }

    /**
     * {@code GET  /payout-bonuses} : get all the payoutBonuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of payoutBonuses in body.
     */
    @GetMapping("/payout-bonuses")
    public List<PayoutBonus> getAllPayoutBonuses() {
        log.debug("REST request to get all PayoutBonuses");
        return payoutBonusService.findAll();
    }

    /**
     * {@code GET  /payout-bonuses/:id} : get the "id" payoutBonus.
     *
     * @param id the id of the payoutBonus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the payoutBonus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payout-bonuses/{id}")
    public ResponseEntity<PayoutBonus> getPayoutBonus(@PathVariable String id) {
        log.debug("REST request to get PayoutBonus : {}", id);
        Optional<PayoutBonus> payoutBonus = payoutBonusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(payoutBonus);
    }

    /**
     * {@code DELETE  /payout-bonuses/:id} : delete the "id" payoutBonus.
     *
     * @param id the id of the payoutBonus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payout-bonuses/{id}")
    public ResponseEntity<Void> deletePayoutBonus(@PathVariable String id) {
        log.debug("REST request to delete PayoutBonus : {}", id);
        payoutBonusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
