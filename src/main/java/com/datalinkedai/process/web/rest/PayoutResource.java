package com.datalinkedai.process.web.rest;

import com.datalinkedai.process.domain.Payout;
import com.datalinkedai.process.repository.PayoutRepository;
import com.datalinkedai.process.service.PayoutService;
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
 * REST controller for managing {@link com.datalinkedai.process.domain.Payout}.
 */
@RestController
@RequestMapping("/api")
public class PayoutResource {

    private final Logger log = LoggerFactory.getLogger(PayoutResource.class);

    private static final String ENTITY_NAME = "processPayout";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PayoutService payoutService;

    private final PayoutRepository payoutRepository;

    public PayoutResource(PayoutService payoutService, PayoutRepository payoutRepository) {
        this.payoutService = payoutService;
        this.payoutRepository = payoutRepository;
    }

    /**
     * {@code POST  /payouts} : Create a new payout.
     *
     * @param payout the payout to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new payout, or with status {@code 400 (Bad Request)} if the payout has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payouts")
    public ResponseEntity<Payout> createPayout(@RequestBody Payout payout) throws URISyntaxException {
        log.debug("REST request to save Payout : {}", payout);
        if (payout.getId() != null) {
            throw new BadRequestAlertException("A new payout cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Payout result = payoutService.save(payout);
        return ResponseEntity
            .created(new URI("/api/payouts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /payouts/:id} : Updates an existing payout.
     *
     * @param id the id of the payout to save.
     * @param payout the payout to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payout,
     * or with status {@code 400 (Bad Request)} if the payout is not valid,
     * or with status {@code 500 (Internal Server Error)} if the payout couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payouts/{id}")
    public ResponseEntity<Payout> updatePayout(@PathVariable(value = "id", required = false) final String id, @RequestBody Payout payout)
        throws URISyntaxException {
        log.debug("REST request to update Payout : {}, {}", id, payout);
        if (payout.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, payout.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!payoutRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Payout result = payoutService.update(payout);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payout.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /payouts/:id} : Partial updates given fields of an existing payout, field will ignore if it is null
     *
     * @param id the id of the payout to save.
     * @param payout the payout to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payout,
     * or with status {@code 400 (Bad Request)} if the payout is not valid,
     * or with status {@code 404 (Not Found)} if the payout is not found,
     * or with status {@code 500 (Internal Server Error)} if the payout couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payouts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Payout> partialUpdatePayout(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Payout payout
    ) throws URISyntaxException {
        log.debug("REST request to partial update Payout partially : {}, {}", id, payout);
        if (payout.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, payout.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!payoutRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Payout> result = payoutService.partialUpdate(payout);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payout.getId()));
    }

    /**
     * {@code GET  /payouts} : get all the payouts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of payouts in body.
     */
    @GetMapping("/payouts")
    public List<Payout> getAllPayouts() {
        log.debug("REST request to get all Payouts");
        return payoutService.findAll();
    }

    /**
     * {@code GET  /payouts/:id} : get the "id" payout.
     *
     * @param id the id of the payout to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the payout, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payouts/{id}")
    public ResponseEntity<Payout> getPayout(@PathVariable String id) {
        log.debug("REST request to get Payout : {}", id);
        Optional<Payout> payout = payoutService.findOne(id);
        return ResponseUtil.wrapOrNotFound(payout);
    }

    /**
     * {@code DELETE  /payouts/:id} : delete the "id" payout.
     *
     * @param id the id of the payout to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payouts/{id}")
    public ResponseEntity<Void> deletePayout(@PathVariable String id) {
        log.debug("REST request to delete Payout : {}", id);
        payoutService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
