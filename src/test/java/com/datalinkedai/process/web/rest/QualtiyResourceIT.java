package com.datalinkedai.process.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.datalinkedai.process.IntegrationTest;
import com.datalinkedai.process.domain.Qualtiy;
import com.datalinkedai.process.domain.enumeration.Status;
import com.datalinkedai.process.repository.QualtiyRepository;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link QualtiyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QualtiyResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_RE_WORK = false;
    private static final Boolean UPDATED_RE_WORK = true;

    private static final Status DEFAULT_RE_WORK_STATUS = Status.ACTIVE;
    private static final Status UPDATED_RE_WORK_STATUS = Status.INACTIVE;

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final Instant DEFAULT_FILE_REACH_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FILE_REACH_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_QC_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_QC_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_QC_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_QC_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_RESULT = 0D;
    private static final Double UPDATED_RESULT = 1D;

    private static final Duration DEFAULT_RE_WORK_DURATION = Duration.ofHours(6);
    private static final Duration UPDATED_RE_WORK_DURATION = Duration.ofHours(12);

    private static final String ENTITY_API_URL = "/api/qualtiys";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private QualtiyRepository qualtiyRepository;

    @Autowired
    private MockMvc restQualtiyMockMvc;

    private Qualtiy qualtiy;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Qualtiy createEntity() {
        Qualtiy qualtiy = new Qualtiy()
            .date(DEFAULT_DATE)
            .reWork(DEFAULT_RE_WORK)
            .reWorkStatus(DEFAULT_RE_WORK_STATUS)
            .remarks(DEFAULT_REMARKS)
            .fileReachDate(DEFAULT_FILE_REACH_DATE)
            .qcStartDate(DEFAULT_QC_START_DATE)
            .qcEndDate(DEFAULT_QC_END_DATE)
            .result(DEFAULT_RESULT)
            .reWorkDuration(DEFAULT_RE_WORK_DURATION);
        return qualtiy;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Qualtiy createUpdatedEntity() {
        Qualtiy qualtiy = new Qualtiy()
            .date(UPDATED_DATE)
            .reWork(UPDATED_RE_WORK)
            .reWorkStatus(UPDATED_RE_WORK_STATUS)
            .remarks(UPDATED_REMARKS)
            .fileReachDate(UPDATED_FILE_REACH_DATE)
            .qcStartDate(UPDATED_QC_START_DATE)
            .qcEndDate(UPDATED_QC_END_DATE)
            .result(UPDATED_RESULT)
            .reWorkDuration(UPDATED_RE_WORK_DURATION);
        return qualtiy;
    }

    @BeforeEach
    public void initTest() {
        qualtiyRepository.deleteAll();
        qualtiy = createEntity();
    }

    @Test
    void createQualtiy() throws Exception {
        int databaseSizeBeforeCreate = qualtiyRepository.findAll().size();
        // Create the Qualtiy
        restQualtiyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qualtiy)))
            .andExpect(status().isCreated());

        // Validate the Qualtiy in the database
        List<Qualtiy> qualtiyList = qualtiyRepository.findAll();
        assertThat(qualtiyList).hasSize(databaseSizeBeforeCreate + 1);
        Qualtiy testQualtiy = qualtiyList.get(qualtiyList.size() - 1);
        assertThat(testQualtiy.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testQualtiy.getReWork()).isEqualTo(DEFAULT_RE_WORK);
        assertThat(testQualtiy.getReWorkStatus()).isEqualTo(DEFAULT_RE_WORK_STATUS);
        assertThat(testQualtiy.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testQualtiy.getFileReachDate()).isEqualTo(DEFAULT_FILE_REACH_DATE);
        assertThat(testQualtiy.getQcStartDate()).isEqualTo(DEFAULT_QC_START_DATE);
        assertThat(testQualtiy.getQcEndDate()).isEqualTo(DEFAULT_QC_END_DATE);
        assertThat(testQualtiy.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testQualtiy.getReWorkDuration()).isEqualTo(DEFAULT_RE_WORK_DURATION);
    }

    @Test
    void createQualtiyWithExistingId() throws Exception {
        // Create the Qualtiy with an existing ID
        qualtiy.setId("existing_id");

        int databaseSizeBeforeCreate = qualtiyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQualtiyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qualtiy)))
            .andExpect(status().isBadRequest());

        // Validate the Qualtiy in the database
        List<Qualtiy> qualtiyList = qualtiyRepository.findAll();
        assertThat(qualtiyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = qualtiyRepository.findAll().size();
        // set the field null
        qualtiy.setDate(null);

        // Create the Qualtiy, which fails.

        restQualtiyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qualtiy)))
            .andExpect(status().isBadRequest());

        List<Qualtiy> qualtiyList = qualtiyRepository.findAll();
        assertThat(qualtiyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllQualtiys() throws Exception {
        // Initialize the database
        qualtiyRepository.save(qualtiy);

        // Get all the qualtiyList
        restQualtiyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qualtiy.getId())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].reWork").value(hasItem(DEFAULT_RE_WORK.booleanValue())))
            .andExpect(jsonPath("$.[*].reWorkStatus").value(hasItem(DEFAULT_RE_WORK_STATUS.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].fileReachDate").value(hasItem(DEFAULT_FILE_REACH_DATE.toString())))
            .andExpect(jsonPath("$.[*].qcStartDate").value(hasItem(DEFAULT_QC_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].qcEndDate").value(hasItem(DEFAULT_QC_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT.doubleValue())))
            .andExpect(jsonPath("$.[*].reWorkDuration").value(hasItem(DEFAULT_RE_WORK_DURATION.toString())));
    }

    @Test
    void getQualtiy() throws Exception {
        // Initialize the database
        qualtiyRepository.save(qualtiy);

        // Get the qualtiy
        restQualtiyMockMvc
            .perform(get(ENTITY_API_URL_ID, qualtiy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qualtiy.getId()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.reWork").value(DEFAULT_RE_WORK.booleanValue()))
            .andExpect(jsonPath("$.reWorkStatus").value(DEFAULT_RE_WORK_STATUS.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS))
            .andExpect(jsonPath("$.fileReachDate").value(DEFAULT_FILE_REACH_DATE.toString()))
            .andExpect(jsonPath("$.qcStartDate").value(DEFAULT_QC_START_DATE.toString()))
            .andExpect(jsonPath("$.qcEndDate").value(DEFAULT_QC_END_DATE.toString()))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT.doubleValue()))
            .andExpect(jsonPath("$.reWorkDuration").value(DEFAULT_RE_WORK_DURATION.toString()));
    }

    @Test
    void getNonExistingQualtiy() throws Exception {
        // Get the qualtiy
        restQualtiyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewQualtiy() throws Exception {
        // Initialize the database
        qualtiyRepository.save(qualtiy);

        int databaseSizeBeforeUpdate = qualtiyRepository.findAll().size();

        // Update the qualtiy
        Qualtiy updatedQualtiy = qualtiyRepository.findById(qualtiy.getId()).get();
        updatedQualtiy
            .date(UPDATED_DATE)
            .reWork(UPDATED_RE_WORK)
            .reWorkStatus(UPDATED_RE_WORK_STATUS)
            .remarks(UPDATED_REMARKS)
            .fileReachDate(UPDATED_FILE_REACH_DATE)
            .qcStartDate(UPDATED_QC_START_DATE)
            .qcEndDate(UPDATED_QC_END_DATE)
            .result(UPDATED_RESULT)
            .reWorkDuration(UPDATED_RE_WORK_DURATION);

        restQualtiyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedQualtiy.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedQualtiy))
            )
            .andExpect(status().isOk());

        // Validate the Qualtiy in the database
        List<Qualtiy> qualtiyList = qualtiyRepository.findAll();
        assertThat(qualtiyList).hasSize(databaseSizeBeforeUpdate);
        Qualtiy testQualtiy = qualtiyList.get(qualtiyList.size() - 1);
        assertThat(testQualtiy.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testQualtiy.getReWork()).isEqualTo(UPDATED_RE_WORK);
        assertThat(testQualtiy.getReWorkStatus()).isEqualTo(UPDATED_RE_WORK_STATUS);
        assertThat(testQualtiy.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testQualtiy.getFileReachDate()).isEqualTo(UPDATED_FILE_REACH_DATE);
        assertThat(testQualtiy.getQcStartDate()).isEqualTo(UPDATED_QC_START_DATE);
        assertThat(testQualtiy.getQcEndDate()).isEqualTo(UPDATED_QC_END_DATE);
        assertThat(testQualtiy.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testQualtiy.getReWorkDuration()).isEqualTo(UPDATED_RE_WORK_DURATION);
    }

    @Test
    void putNonExistingQualtiy() throws Exception {
        int databaseSizeBeforeUpdate = qualtiyRepository.findAll().size();
        qualtiy.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQualtiyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qualtiy.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qualtiy))
            )
            .andExpect(status().isBadRequest());

        // Validate the Qualtiy in the database
        List<Qualtiy> qualtiyList = qualtiyRepository.findAll();
        assertThat(qualtiyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchQualtiy() throws Exception {
        int databaseSizeBeforeUpdate = qualtiyRepository.findAll().size();
        qualtiy.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQualtiyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qualtiy))
            )
            .andExpect(status().isBadRequest());

        // Validate the Qualtiy in the database
        List<Qualtiy> qualtiyList = qualtiyRepository.findAll();
        assertThat(qualtiyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamQualtiy() throws Exception {
        int databaseSizeBeforeUpdate = qualtiyRepository.findAll().size();
        qualtiy.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQualtiyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qualtiy)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Qualtiy in the database
        List<Qualtiy> qualtiyList = qualtiyRepository.findAll();
        assertThat(qualtiyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateQualtiyWithPatch() throws Exception {
        // Initialize the database
        qualtiyRepository.save(qualtiy);

        int databaseSizeBeforeUpdate = qualtiyRepository.findAll().size();

        // Update the qualtiy using partial update
        Qualtiy partialUpdatedQualtiy = new Qualtiy();
        partialUpdatedQualtiy.setId(qualtiy.getId());

        partialUpdatedQualtiy
            .date(UPDATED_DATE)
            .reWorkStatus(UPDATED_RE_WORK_STATUS)
            .remarks(UPDATED_REMARKS)
            .fileReachDate(UPDATED_FILE_REACH_DATE)
            .qcEndDate(UPDATED_QC_END_DATE)
            .result(UPDATED_RESULT)
            .reWorkDuration(UPDATED_RE_WORK_DURATION);

        restQualtiyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQualtiy.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQualtiy))
            )
            .andExpect(status().isOk());

        // Validate the Qualtiy in the database
        List<Qualtiy> qualtiyList = qualtiyRepository.findAll();
        assertThat(qualtiyList).hasSize(databaseSizeBeforeUpdate);
        Qualtiy testQualtiy = qualtiyList.get(qualtiyList.size() - 1);
        assertThat(testQualtiy.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testQualtiy.getReWork()).isEqualTo(DEFAULT_RE_WORK);
        assertThat(testQualtiy.getReWorkStatus()).isEqualTo(UPDATED_RE_WORK_STATUS);
        assertThat(testQualtiy.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testQualtiy.getFileReachDate()).isEqualTo(UPDATED_FILE_REACH_DATE);
        assertThat(testQualtiy.getQcStartDate()).isEqualTo(DEFAULT_QC_START_DATE);
        assertThat(testQualtiy.getQcEndDate()).isEqualTo(UPDATED_QC_END_DATE);
        assertThat(testQualtiy.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testQualtiy.getReWorkDuration()).isEqualTo(UPDATED_RE_WORK_DURATION);
    }

    @Test
    void fullUpdateQualtiyWithPatch() throws Exception {
        // Initialize the database
        qualtiyRepository.save(qualtiy);

        int databaseSizeBeforeUpdate = qualtiyRepository.findAll().size();

        // Update the qualtiy using partial update
        Qualtiy partialUpdatedQualtiy = new Qualtiy();
        partialUpdatedQualtiy.setId(qualtiy.getId());

        partialUpdatedQualtiy
            .date(UPDATED_DATE)
            .reWork(UPDATED_RE_WORK)
            .reWorkStatus(UPDATED_RE_WORK_STATUS)
            .remarks(UPDATED_REMARKS)
            .fileReachDate(UPDATED_FILE_REACH_DATE)
            .qcStartDate(UPDATED_QC_START_DATE)
            .qcEndDate(UPDATED_QC_END_DATE)
            .result(UPDATED_RESULT)
            .reWorkDuration(UPDATED_RE_WORK_DURATION);

        restQualtiyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQualtiy.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQualtiy))
            )
            .andExpect(status().isOk());

        // Validate the Qualtiy in the database
        List<Qualtiy> qualtiyList = qualtiyRepository.findAll();
        assertThat(qualtiyList).hasSize(databaseSizeBeforeUpdate);
        Qualtiy testQualtiy = qualtiyList.get(qualtiyList.size() - 1);
        assertThat(testQualtiy.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testQualtiy.getReWork()).isEqualTo(UPDATED_RE_WORK);
        assertThat(testQualtiy.getReWorkStatus()).isEqualTo(UPDATED_RE_WORK_STATUS);
        assertThat(testQualtiy.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testQualtiy.getFileReachDate()).isEqualTo(UPDATED_FILE_REACH_DATE);
        assertThat(testQualtiy.getQcStartDate()).isEqualTo(UPDATED_QC_START_DATE);
        assertThat(testQualtiy.getQcEndDate()).isEqualTo(UPDATED_QC_END_DATE);
        assertThat(testQualtiy.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testQualtiy.getReWorkDuration()).isEqualTo(UPDATED_RE_WORK_DURATION);
    }

    @Test
    void patchNonExistingQualtiy() throws Exception {
        int databaseSizeBeforeUpdate = qualtiyRepository.findAll().size();
        qualtiy.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQualtiyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, qualtiy.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qualtiy))
            )
            .andExpect(status().isBadRequest());

        // Validate the Qualtiy in the database
        List<Qualtiy> qualtiyList = qualtiyRepository.findAll();
        assertThat(qualtiyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchQualtiy() throws Exception {
        int databaseSizeBeforeUpdate = qualtiyRepository.findAll().size();
        qualtiy.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQualtiyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qualtiy))
            )
            .andExpect(status().isBadRequest());

        // Validate the Qualtiy in the database
        List<Qualtiy> qualtiyList = qualtiyRepository.findAll();
        assertThat(qualtiyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamQualtiy() throws Exception {
        int databaseSizeBeforeUpdate = qualtiyRepository.findAll().size();
        qualtiy.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQualtiyMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(qualtiy)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Qualtiy in the database
        List<Qualtiy> qualtiyList = qualtiyRepository.findAll();
        assertThat(qualtiyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteQualtiy() throws Exception {
        // Initialize the database
        qualtiyRepository.save(qualtiy);

        int databaseSizeBeforeDelete = qualtiyRepository.findAll().size();

        // Delete the qualtiy
        restQualtiyMockMvc
            .perform(delete(ENTITY_API_URL_ID, qualtiy.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Qualtiy> qualtiyList = qualtiyRepository.findAll();
        assertThat(qualtiyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
