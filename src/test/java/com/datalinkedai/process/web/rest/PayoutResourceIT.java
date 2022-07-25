package com.datalinkedai.process.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.datalinkedai.process.IntegrationTest;
import com.datalinkedai.process.domain.Payout;
import com.datalinkedai.process.repository.PayoutRepository;
import java.time.Duration;
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
 * Integration tests for the {@link PayoutResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PayoutResourceIT {

    private static final Duration DEFAULT_TOTAL_DURATION = Duration.ofHours(6);
    private static final Duration UPDATED_TOTAL_DURATION = Duration.ofHours(12);

    private static final Double DEFAULT_COST_PER_DURATION = 1D;
    private static final Double UPDATED_COST_PER_DURATION = 2D;

    private static final Double DEFAULT_TOTAL_COST = 1D;
    private static final Double UPDATED_TOTAL_COST = 2D;

    private static final Double DEFAULT_BONUS_PAYOUT = 1D;
    private static final Double UPDATED_BONUS_PAYOUT = 2D;

    private static final Double DEFAULT_COST_PER_DURATION_BONUS = 1D;
    private static final Double UPDATED_COST_PER_DURATION_BONUS = 2D;

    private static final Duration DEFAULT_TOTAL_DURATION_BONUS = Duration.ofHours(6);
    private static final Duration UPDATED_TOTAL_DURATION_BONUS = Duration.ofHours(12);

    private static final Double DEFAULT_TOTAL_COST_BONUS = 1D;
    private static final Double UPDATED_TOTAL_COST_BONUS = 2D;

    private static final Double DEFAULT_PF_AMOUNT = 1D;
    private static final Double UPDATED_PF_AMOUNT = 2D;

    private static final Double DEFAULT_ESI_AMOUNT = 1D;
    private static final Double UPDATED_ESI_AMOUNT = 2D;

    private static final String ENTITY_API_URL = "/api/payouts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PayoutRepository payoutRepository;

    @Autowired
    private MockMvc restPayoutMockMvc;

    private Payout payout;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payout createEntity() {
        Payout payout = new Payout()
            .totalDuration(DEFAULT_TOTAL_DURATION)
            .costPerDuration(DEFAULT_COST_PER_DURATION)
            .totalCost(DEFAULT_TOTAL_COST)
            .bonusPayout(DEFAULT_BONUS_PAYOUT)
            .costPerDurationBonus(DEFAULT_COST_PER_DURATION_BONUS)
            .totalDurationBonus(DEFAULT_TOTAL_DURATION_BONUS)
            .totalCostBonus(DEFAULT_TOTAL_COST_BONUS)
            .pfAmount(DEFAULT_PF_AMOUNT)
            .esiAmount(DEFAULT_ESI_AMOUNT);
        return payout;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payout createUpdatedEntity() {
        Payout payout = new Payout()
            .totalDuration(UPDATED_TOTAL_DURATION)
            .costPerDuration(UPDATED_COST_PER_DURATION)
            .totalCost(UPDATED_TOTAL_COST)
            .bonusPayout(UPDATED_BONUS_PAYOUT)
            .costPerDurationBonus(UPDATED_COST_PER_DURATION_BONUS)
            .totalDurationBonus(UPDATED_TOTAL_DURATION_BONUS)
            .totalCostBonus(UPDATED_TOTAL_COST_BONUS)
            .pfAmount(UPDATED_PF_AMOUNT)
            .esiAmount(UPDATED_ESI_AMOUNT);
        return payout;
    }

    @BeforeEach
    public void initTest() {
        payoutRepository.deleteAll();
        payout = createEntity();
    }

    @Test
    void createPayout() throws Exception {
        int databaseSizeBeforeCreate = payoutRepository.findAll().size();
        // Create the Payout
        restPayoutMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payout)))
            .andExpect(status().isCreated());

        // Validate the Payout in the database
        List<Payout> payoutList = payoutRepository.findAll();
        assertThat(payoutList).hasSize(databaseSizeBeforeCreate + 1);
        Payout testPayout = payoutList.get(payoutList.size() - 1);
        assertThat(testPayout.getTotalDuration()).isEqualTo(DEFAULT_TOTAL_DURATION);
        assertThat(testPayout.getCostPerDuration()).isEqualTo(DEFAULT_COST_PER_DURATION);
        assertThat(testPayout.getTotalCost()).isEqualTo(DEFAULT_TOTAL_COST);
        assertThat(testPayout.getBonusPayout()).isEqualTo(DEFAULT_BONUS_PAYOUT);
        assertThat(testPayout.getCostPerDurationBonus()).isEqualTo(DEFAULT_COST_PER_DURATION_BONUS);
        assertThat(testPayout.getTotalDurationBonus()).isEqualTo(DEFAULT_TOTAL_DURATION_BONUS);
        assertThat(testPayout.getTotalCostBonus()).isEqualTo(DEFAULT_TOTAL_COST_BONUS);
        assertThat(testPayout.getPfAmount()).isEqualTo(DEFAULT_PF_AMOUNT);
        assertThat(testPayout.getEsiAmount()).isEqualTo(DEFAULT_ESI_AMOUNT);
    }

    @Test
    void createPayoutWithExistingId() throws Exception {
        // Create the Payout with an existing ID
        payout.setId("existing_id");

        int databaseSizeBeforeCreate = payoutRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayoutMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payout)))
            .andExpect(status().isBadRequest());

        // Validate the Payout in the database
        List<Payout> payoutList = payoutRepository.findAll();
        assertThat(payoutList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllPayouts() throws Exception {
        // Initialize the database
        payoutRepository.save(payout);

        // Get all the payoutList
        restPayoutMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payout.getId())))
            .andExpect(jsonPath("$.[*].totalDuration").value(hasItem(DEFAULT_TOTAL_DURATION.toString())))
            .andExpect(jsonPath("$.[*].costPerDuration").value(hasItem(DEFAULT_COST_PER_DURATION.doubleValue())))
            .andExpect(jsonPath("$.[*].totalCost").value(hasItem(DEFAULT_TOTAL_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].bonusPayout").value(hasItem(DEFAULT_BONUS_PAYOUT.doubleValue())))
            .andExpect(jsonPath("$.[*].costPerDurationBonus").value(hasItem(DEFAULT_COST_PER_DURATION_BONUS.doubleValue())))
            .andExpect(jsonPath("$.[*].totalDurationBonus").value(hasItem(DEFAULT_TOTAL_DURATION_BONUS.toString())))
            .andExpect(jsonPath("$.[*].totalCostBonus").value(hasItem(DEFAULT_TOTAL_COST_BONUS.doubleValue())))
            .andExpect(jsonPath("$.[*].pfAmount").value(hasItem(DEFAULT_PF_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].esiAmount").value(hasItem(DEFAULT_ESI_AMOUNT.doubleValue())));
    }

    @Test
    void getPayout() throws Exception {
        // Initialize the database
        payoutRepository.save(payout);

        // Get the payout
        restPayoutMockMvc
            .perform(get(ENTITY_API_URL_ID, payout.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(payout.getId()))
            .andExpect(jsonPath("$.totalDuration").value(DEFAULT_TOTAL_DURATION.toString()))
            .andExpect(jsonPath("$.costPerDuration").value(DEFAULT_COST_PER_DURATION.doubleValue()))
            .andExpect(jsonPath("$.totalCost").value(DEFAULT_TOTAL_COST.doubleValue()))
            .andExpect(jsonPath("$.bonusPayout").value(DEFAULT_BONUS_PAYOUT.doubleValue()))
            .andExpect(jsonPath("$.costPerDurationBonus").value(DEFAULT_COST_PER_DURATION_BONUS.doubleValue()))
            .andExpect(jsonPath("$.totalDurationBonus").value(DEFAULT_TOTAL_DURATION_BONUS.toString()))
            .andExpect(jsonPath("$.totalCostBonus").value(DEFAULT_TOTAL_COST_BONUS.doubleValue()))
            .andExpect(jsonPath("$.pfAmount").value(DEFAULT_PF_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.esiAmount").value(DEFAULT_ESI_AMOUNT.doubleValue()));
    }

    @Test
    void getNonExistingPayout() throws Exception {
        // Get the payout
        restPayoutMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewPayout() throws Exception {
        // Initialize the database
        payoutRepository.save(payout);

        int databaseSizeBeforeUpdate = payoutRepository.findAll().size();

        // Update the payout
        Payout updatedPayout = payoutRepository.findById(payout.getId()).get();
        updatedPayout
            .totalDuration(UPDATED_TOTAL_DURATION)
            .costPerDuration(UPDATED_COST_PER_DURATION)
            .totalCost(UPDATED_TOTAL_COST)
            .bonusPayout(UPDATED_BONUS_PAYOUT)
            .costPerDurationBonus(UPDATED_COST_PER_DURATION_BONUS)
            .totalDurationBonus(UPDATED_TOTAL_DURATION_BONUS)
            .totalCostBonus(UPDATED_TOTAL_COST_BONUS)
            .pfAmount(UPDATED_PF_AMOUNT)
            .esiAmount(UPDATED_ESI_AMOUNT);

        restPayoutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPayout.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPayout))
            )
            .andExpect(status().isOk());

        // Validate the Payout in the database
        List<Payout> payoutList = payoutRepository.findAll();
        assertThat(payoutList).hasSize(databaseSizeBeforeUpdate);
        Payout testPayout = payoutList.get(payoutList.size() - 1);
        assertThat(testPayout.getTotalDuration()).isEqualTo(UPDATED_TOTAL_DURATION);
        assertThat(testPayout.getCostPerDuration()).isEqualTo(UPDATED_COST_PER_DURATION);
        assertThat(testPayout.getTotalCost()).isEqualTo(UPDATED_TOTAL_COST);
        assertThat(testPayout.getBonusPayout()).isEqualTo(UPDATED_BONUS_PAYOUT);
        assertThat(testPayout.getCostPerDurationBonus()).isEqualTo(UPDATED_COST_PER_DURATION_BONUS);
        assertThat(testPayout.getTotalDurationBonus()).isEqualTo(UPDATED_TOTAL_DURATION_BONUS);
        assertThat(testPayout.getTotalCostBonus()).isEqualTo(UPDATED_TOTAL_COST_BONUS);
        assertThat(testPayout.getPfAmount()).isEqualTo(UPDATED_PF_AMOUNT);
        assertThat(testPayout.getEsiAmount()).isEqualTo(UPDATED_ESI_AMOUNT);
    }

    @Test
    void putNonExistingPayout() throws Exception {
        int databaseSizeBeforeUpdate = payoutRepository.findAll().size();
        payout.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayoutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, payout.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payout))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payout in the database
        List<Payout> payoutList = payoutRepository.findAll();
        assertThat(payoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPayout() throws Exception {
        int databaseSizeBeforeUpdate = payoutRepository.findAll().size();
        payout.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayoutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payout))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payout in the database
        List<Payout> payoutList = payoutRepository.findAll();
        assertThat(payoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPayout() throws Exception {
        int databaseSizeBeforeUpdate = payoutRepository.findAll().size();
        payout.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayoutMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payout)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Payout in the database
        List<Payout> payoutList = payoutRepository.findAll();
        assertThat(payoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePayoutWithPatch() throws Exception {
        // Initialize the database
        payoutRepository.save(payout);

        int databaseSizeBeforeUpdate = payoutRepository.findAll().size();

        // Update the payout using partial update
        Payout partialUpdatedPayout = new Payout();
        partialUpdatedPayout.setId(payout.getId());

        partialUpdatedPayout
            .totalCost(UPDATED_TOTAL_COST)
            .bonusPayout(UPDATED_BONUS_PAYOUT)
            .costPerDurationBonus(UPDATED_COST_PER_DURATION_BONUS)
            .totalDurationBonus(UPDATED_TOTAL_DURATION_BONUS)
            .totalCostBonus(UPDATED_TOTAL_COST_BONUS);

        restPayoutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayout.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayout))
            )
            .andExpect(status().isOk());

        // Validate the Payout in the database
        List<Payout> payoutList = payoutRepository.findAll();
        assertThat(payoutList).hasSize(databaseSizeBeforeUpdate);
        Payout testPayout = payoutList.get(payoutList.size() - 1);
        assertThat(testPayout.getTotalDuration()).isEqualTo(DEFAULT_TOTAL_DURATION);
        assertThat(testPayout.getCostPerDuration()).isEqualTo(DEFAULT_COST_PER_DURATION);
        assertThat(testPayout.getTotalCost()).isEqualTo(UPDATED_TOTAL_COST);
        assertThat(testPayout.getBonusPayout()).isEqualTo(UPDATED_BONUS_PAYOUT);
        assertThat(testPayout.getCostPerDurationBonus()).isEqualTo(UPDATED_COST_PER_DURATION_BONUS);
        assertThat(testPayout.getTotalDurationBonus()).isEqualTo(UPDATED_TOTAL_DURATION_BONUS);
        assertThat(testPayout.getTotalCostBonus()).isEqualTo(UPDATED_TOTAL_COST_BONUS);
        assertThat(testPayout.getPfAmount()).isEqualTo(DEFAULT_PF_AMOUNT);
        assertThat(testPayout.getEsiAmount()).isEqualTo(DEFAULT_ESI_AMOUNT);
    }

    @Test
    void fullUpdatePayoutWithPatch() throws Exception {
        // Initialize the database
        payoutRepository.save(payout);

        int databaseSizeBeforeUpdate = payoutRepository.findAll().size();

        // Update the payout using partial update
        Payout partialUpdatedPayout = new Payout();
        partialUpdatedPayout.setId(payout.getId());

        partialUpdatedPayout
            .totalDuration(UPDATED_TOTAL_DURATION)
            .costPerDuration(UPDATED_COST_PER_DURATION)
            .totalCost(UPDATED_TOTAL_COST)
            .bonusPayout(UPDATED_BONUS_PAYOUT)
            .costPerDurationBonus(UPDATED_COST_PER_DURATION_BONUS)
            .totalDurationBonus(UPDATED_TOTAL_DURATION_BONUS)
            .totalCostBonus(UPDATED_TOTAL_COST_BONUS)
            .pfAmount(UPDATED_PF_AMOUNT)
            .esiAmount(UPDATED_ESI_AMOUNT);

        restPayoutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayout.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayout))
            )
            .andExpect(status().isOk());

        // Validate the Payout in the database
        List<Payout> payoutList = payoutRepository.findAll();
        assertThat(payoutList).hasSize(databaseSizeBeforeUpdate);
        Payout testPayout = payoutList.get(payoutList.size() - 1);
        assertThat(testPayout.getTotalDuration()).isEqualTo(UPDATED_TOTAL_DURATION);
        assertThat(testPayout.getCostPerDuration()).isEqualTo(UPDATED_COST_PER_DURATION);
        assertThat(testPayout.getTotalCost()).isEqualTo(UPDATED_TOTAL_COST);
        assertThat(testPayout.getBonusPayout()).isEqualTo(UPDATED_BONUS_PAYOUT);
        assertThat(testPayout.getCostPerDurationBonus()).isEqualTo(UPDATED_COST_PER_DURATION_BONUS);
        assertThat(testPayout.getTotalDurationBonus()).isEqualTo(UPDATED_TOTAL_DURATION_BONUS);
        assertThat(testPayout.getTotalCostBonus()).isEqualTo(UPDATED_TOTAL_COST_BONUS);
        assertThat(testPayout.getPfAmount()).isEqualTo(UPDATED_PF_AMOUNT);
        assertThat(testPayout.getEsiAmount()).isEqualTo(UPDATED_ESI_AMOUNT);
    }

    @Test
    void patchNonExistingPayout() throws Exception {
        int databaseSizeBeforeUpdate = payoutRepository.findAll().size();
        payout.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayoutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, payout.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payout))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payout in the database
        List<Payout> payoutList = payoutRepository.findAll();
        assertThat(payoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPayout() throws Exception {
        int databaseSizeBeforeUpdate = payoutRepository.findAll().size();
        payout.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayoutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payout))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payout in the database
        List<Payout> payoutList = payoutRepository.findAll();
        assertThat(payoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPayout() throws Exception {
        int databaseSizeBeforeUpdate = payoutRepository.findAll().size();
        payout.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayoutMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(payout)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Payout in the database
        List<Payout> payoutList = payoutRepository.findAll();
        assertThat(payoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePayout() throws Exception {
        // Initialize the database
        payoutRepository.save(payout);

        int databaseSizeBeforeDelete = payoutRepository.findAll().size();

        // Delete the payout
        restPayoutMockMvc
            .perform(delete(ENTITY_API_URL_ID, payout.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Payout> payoutList = payoutRepository.findAll();
        assertThat(payoutList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
