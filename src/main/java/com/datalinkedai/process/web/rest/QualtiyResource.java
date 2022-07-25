package com.datalinkedai.process.web.rest;

import com.datalinkedai.process.domain.Qualtiy;
import com.datalinkedai.process.repository.QualtiyRepository;
import com.datalinkedai.process.service.QualtiyService;
import com.datalinkedai.process.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.datalinkedai.process.domain.Qualtiy}.
 */
@RestController
@RequestMapping("/api")
public class QualtiyResource {

    private final Logger log = LoggerFactory.getLogger(QualtiyResource.class);

    private static final String ENTITY_NAME = "processQualtiy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QualtiyService qualtiyService;

    private final QualtiyRepository qualtiyRepository;

    public QualtiyResource(QualtiyService qualtiyService, QualtiyRepository qualtiyRepository) {
        this.qualtiyService = qualtiyService;
        this.qualtiyRepository = qualtiyRepository;
    }

    /**
     * {@code POST  /qualtiys} : Create a new qualtiy.
     *
     * @param qualtiy the qualtiy to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new qualtiy, or with status {@code 400 (Bad Request)} if the qualtiy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/qualtiys")
    public ResponseEntity<Qualtiy> createQualtiy(@Valid @RequestBody Qualtiy qualtiy) throws URISyntaxException {
        log.debug("REST request to save Qualtiy : {}", qualtiy);
        if (qualtiy.getId() != null) {
            throw new BadRequestAlertException("A new qualtiy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Qualtiy result = qualtiyService.save(qualtiy);
        return ResponseEntity
            .created(new URI("/api/qualtiys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /qualtiys/:id} : Updates an existing qualtiy.
     *
     * @param id the id of the qualtiy to save.
     * @param qualtiy the qualtiy to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qualtiy,
     * or with status {@code 400 (Bad Request)} if the qualtiy is not valid,
     * or with status {@code 500 (Internal Server Error)} if the qualtiy couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/qualtiys/{id}")
    public ResponseEntity<Qualtiy> updateQualtiy(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody Qualtiy qualtiy
    ) throws URISyntaxException {
        log.debug("REST request to update Qualtiy : {}, {}", id, qualtiy);
        if (qualtiy.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qualtiy.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qualtiyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Qualtiy result = qualtiyService.update(qualtiy);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qualtiy.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /qualtiys/:id} : Partial updates given fields of an existing qualtiy, field will ignore if it is null
     *
     * @param id the id of the qualtiy to save.
     * @param qualtiy the qualtiy to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qualtiy,
     * or with status {@code 400 (Bad Request)} if the qualtiy is not valid,
     * or with status {@code 404 (Not Found)} if the qualtiy is not found,
     * or with status {@code 500 (Internal Server Error)} if the qualtiy couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/qualtiys/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Qualtiy> partialUpdateQualtiy(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody Qualtiy qualtiy
    ) throws URISyntaxException {
        log.debug("REST request to partial update Qualtiy partially : {}, {}", id, qualtiy);
        if (qualtiy.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qualtiy.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qualtiyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Qualtiy> result = qualtiyService.partialUpdate(qualtiy);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qualtiy.getId()));
    }

    /**
     * {@code GET  /qualtiys} : get all the qualtiys.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qualtiys in body.
     */
    @GetMapping("/qualtiys")
    public List<Qualtiy> getAllQualtiys() {
        log.debug("REST request to get all Qualtiys");
        return qualtiyService.findAll();
    }

    /**
     * {@code GET  /qualtiys/:id} : get the "id" qualtiy.
     *
     * @param id the id of the qualtiy to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the qualtiy, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/qualtiys/{id}")
    public ResponseEntity<Qualtiy> getQualtiy(@PathVariable String id) {
        log.debug("REST request to get Qualtiy : {}", id);
        Optional<Qualtiy> qualtiy = qualtiyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(qualtiy);
    }

    /**
     * {@code DELETE  /qualtiys/:id} : delete the "id" qualtiy.
     *
     * @param id the id of the qualtiy to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/qualtiys/{id}")
    public ResponseEntity<Void> deleteQualtiy(@PathVariable String id) {
        log.debug("REST request to delete Qualtiy : {}", id);
        qualtiyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
