package com.datalinkedai.process.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.datalinkedai.process.IntegrationTest;
import com.datalinkedai.process.domain.PayoutBonus;
import com.datalinkedai.process.repository.PayoutBonusRepository;
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
 * Integration tests for the {@link PayoutBonusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PayoutBonusResourceIT {

    private static final Duration DEFAULT_BASIC_DURATION = Duration.ofHours(6);
    private static final Duration UPDATED_BASIC_DURATION = Duration.ofHours(12);

    private static final Double DEFAULT_BASIC_COST = 1D;
    private static final Double UPDATED_BASIC_COST = 2D;

    private static final Duration DEFAULT_BONUS_DURATION = Duration.ofHours(6);
    private static final Duration UPDATED_BONUS_DURATION = Duration.ofHours(12);

    private static final Double DEFAULT_BONUS_COST = 1D;
    private static final Double UPDATED_BONUS_COST = 2D;

    private static final String ENTITY_API_URL = "/api/payout-bonuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PayoutBonusRepository payoutBonusRepository;

    @Autowired
    private MockMvc restPayoutBonusMockMvc;

    private PayoutBonus payoutBonus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PayoutBonus createEntity() {
        PayoutBonus payoutBonus = new PayoutBonus()
            .basicDuration(DEFAULT_BASIC_DURATION)
            .basicCost(DEFAULT_BASIC_COST)
            .bonusDuration(DEFAULT_BONUS_DURATION)
            .bonusCost(DEFAULT_BONUS_COST);
        return payoutBonus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PayoutBonus createUpdatedEntity() {
        PayoutBonus payoutBonus = new PayoutBonus()
            .basicDuration(UPDATED_BASIC_DURATION)
            .basicCost(UPDATED_BASIC_COST)
            .bonusDuration(UPDATED_BONUS_DURATION)
            .bonusCost(UPDATED_BONUS_COST);
        return payoutBonus;
    }

    @BeforeEach
    public void initTest() {
        payoutBonusRepository.deleteAll();
        payoutBonus = createEntity();
    }

    @Test
    void createPayoutBonus() throws Exception {
        int databaseSizeBeforeCreate = payoutBonusRepository.findAll().size();
        // Create the PayoutBonus
        restPayoutBonusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payoutBonus)))
            .andExpect(status().isCreated());

        // Validate the PayoutBonus in the database
        List<PayoutBonus> payoutBonusList = payoutBonusRepository.findAll();
        assertThat(payoutBonusList).hasSize(databaseSizeBeforeCreate + 1);
        PayoutBonus testPayoutBonus = payoutBonusList.get(payoutBonusList.size() - 1);
        assertThat(testPayoutBonus.getBasicDuration()).isEqualTo(DEFAULT_BASIC_DURATION);
        assertThat(testPayoutBonus.getBasicCost()).isEqualTo(DEFAULT_BASIC_COST);
        assertThat(testPayoutBonus.getBonusDuration()).isEqualTo(DEFAULT_BONUS_DURATION);
        assertThat(testPayoutBonus.getBonusCost()).isEqualTo(DEFAULT_BONUS_COST);
    }

    @Test
    void createPayoutBonusWithExistingId() throws Exception {
        // Create the PayoutBonus with an existing ID
        payoutBonus.setId("existing_id");

        int databaseSizeBeforeCreate = payoutBonusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayoutBonusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payoutBonus)))
            .andExpect(status().isBadRequest());

        // Validate the PayoutBonus in the database
        List<PayoutBonus> payoutBonusList = payoutBonusRepository.findAll();
        assertThat(payoutBonusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllPayoutBonuses() throws Exception {
        // Initialize the database
        payoutBonusRepository.save(payoutBonus);

        // Get all the payoutBonusList
        restPayoutBonusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payoutBonus.getId())))
            .andExpect(jsonPath("$.[*].basicDuration").value(hasItem(DEFAULT_BASIC_DURATION.toString())))
            .andExpect(jsonPath("$.[*].basicCost").value(hasItem(DEFAULT_BASIC_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].bonusDuration").value(hasItem(DEFAULT_BONUS_DURATION.toString())))
            .andExpect(jsonPath("$.[*].bonusCost").value(hasItem(DEFAULT_BONUS_COST.doubleValue())));
    }

    @Test
    void getPayoutBonus() throws Exception {
        // Initialize the database
        payoutBonusRepository.save(payoutBonus);

        // Get the payoutBonus
        restPayoutBonusMockMvc
            .perform(get(ENTITY_API_URL_ID, payoutBonus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(payoutBonus.getId()))
            .andExpect(jsonPath("$.basicDuration").value(DEFAULT_BASIC_DURATION.toString()))
            .andExpect(jsonPath("$.basicCost").value(DEFAULT_BASIC_COST.doubleValue()))
            .andExpect(jsonPath("$.bonusDuration").value(DEFAULT_BONUS_DURATION.toString()))
            .andExpect(jsonPath("$.bonusCost").value(DEFAULT_BONUS_COST.doubleValue()));
    }

    @Test
    void getNonExistingPayoutBonus() throws Exception {
        // Get the payoutBonus
        restPayoutBonusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewPayoutBonus() throws Exception {
        // Initialize the database
        payoutBonusRepository.save(payoutBonus);

        int databaseSizeBeforeUpdate = payoutBonusRepository.findAll().size();

        // Update the payoutBonus
        PayoutBonus updatedPayoutBonus = payoutBonusRepository.findById(payoutBonus.getId()).get();
        updatedPayoutBonus
            .basicDuration(UPDATED_BASIC_DURATION)
            .basicCost(UPDATED_BASIC_COST)
            .bonusDuration(UPDATED_BONUS_DURATION)
            .bonusCost(UPDATED_BONUS_COST);

        restPayoutBonusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPayoutBonus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPayoutBonus))
            )
            .andExpect(status().isOk());

        // Validate the PayoutBonus in the database
        List<PayoutBonus> payoutBonusList = payoutBonusRepository.findAll();
        assertThat(payoutBonusList).hasSize(databaseSizeBeforeUpdate);
        PayoutBonus testPayoutBonus = payoutBonusList.get(payoutBonusList.size() - 1);
        assertThat(testPayoutBonus.getBasicDuration()).isEqualTo(UPDATED_BASIC_DURATION);
        assertThat(testPayoutBonus.getBasicCost()).isEqualTo(UPDATED_BASIC_COST);
        assertThat(testPayoutBonus.getBonusDuration()).isEqualTo(UPDATED_BONUS_DURATION);
        assertThat(testPayoutBonus.getBonusCost()).isEqualTo(UPDATED_BONUS_COST);
    }

    @Test
    void putNonExistingPayoutBonus() throws Exception {
        int databaseSizeBeforeUpdate = payoutBonusRepository.findAll().size();
        payoutBonus.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayoutBonusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, payoutBonus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payoutBonus))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayoutBonus in the database
        List<PayoutBonus> payoutBonusList = payoutBonusRepository.findAll();
        assertThat(payoutBonusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPayoutBonus() throws Exception {
        int databaseSizeBeforeUpdate = payoutBonusRepository.findAll().size();
        payoutBonus.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayoutBonusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payoutBonus))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayoutBonus in the database
        List<PayoutBonus> payoutBonusList = payoutBonusRepository.findAll();
        assertThat(payoutBonusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPayoutBonus() throws Exception {
        int databaseSizeBeforeUpdate = payoutBonusRepository.findAll().size();
        payoutBonus.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayoutBonusMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payoutBonus)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PayoutBonus in the database
        List<PayoutBonus> payoutBonusList = payoutBonusRepository.findAll();
        assertThat(payoutBonusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePayoutBonusWithPatch() throws Exception {
        // Initialize the database
        payoutBonusRepository.save(payoutBonus);

        int databaseSizeBeforeUpdate = payoutBonusRepository.findAll().size();

        // Update the payoutBonus using partial update
        PayoutBonus partialUpdatedPayoutBonus = new PayoutBonus();
        partialUpdatedPayoutBonus.setId(payoutBonus.getId());

        partialUpdatedPayoutBonus.bonusDuration(UPDATED_BONUS_DURATION);

        restPayoutBonusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayoutBonus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayoutBonus))
            )
            .andExpect(status().isOk());

        // Validate the PayoutBonus in the database
        List<PayoutBonus> payoutBonusList = payoutBonusRepository.findAll();
        assertThat(payoutBonusList).hasSize(databaseSizeBeforeUpdate);
        PayoutBonus testPayoutBonus = payoutBonusList.get(payoutBonusList.size() - 1);
        assertThat(testPayoutBonus.getBasicDuration()).isEqualTo(DEFAULT_BASIC_DURATION);
        assertThat(testPayoutBonus.getBasicCost()).isEqualTo(DEFAULT_BASIC_COST);
        assertThat(testPayoutBonus.getBonusDuration()).isEqualTo(UPDATED_BONUS_DURATION);
        assertThat(testPayoutBonus.getBonusCost()).isEqualTo(DEFAULT_BONUS_COST);
    }

    @Test
    void fullUpdatePayoutBonusWithPatch() throws Exception {
        // Initialize the database
        payoutBonusRepository.save(payoutBonus);

        int databaseSizeBeforeUpdate = payoutBonusRepository.findAll().size();

        // Update the payoutBonus using partial update
        PayoutBonus partialUpdatedPayoutBonus = new PayoutBonus();
        partialUpdatedPayoutBonus.setId(payoutBonus.getId());

        partialUpdatedPayoutBonus
            .basicDuration(UPDATED_BASIC_DURATION)
            .basicCost(UPDATED_BASIC_COST)
            .bonusDuration(UPDATED_BONUS_DURATION)
            .bonusCost(UPDATED_BONUS_COST);

        restPayoutBonusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayoutBonus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayoutBonus))
            )
            .andExpect(status().isOk());

        // Validate the PayoutBonus in the database
        List<PayoutBonus> payoutBonusList = payoutBonusRepository.findAll();
        assertThat(payoutBonusList).hasSize(databaseSizeBeforeUpdate);
        PayoutBonus testPayoutBonus = payoutBonusList.get(payoutBonusList.size() - 1);
        assertThat(testPayoutBonus.getBasicDuration()).isEqualTo(UPDATED_BASIC_DURATION);
        assertThat(testPayoutBonus.getBasicCost()).isEqualTo(UPDATED_BASIC_COST);
        assertThat(testPayoutBonus.getBonusDuration()).isEqualTo(UPDATED_BONUS_DURATION);
        assertThat(testPayoutBonus.getBonusCost()).isEqualTo(UPDATED_BONUS_COST);
    }

    @Test
    void patchNonExistingPayoutBonus() throws Exception {
        int databaseSizeBeforeUpdate = payoutBonusRepository.findAll().size();
        payoutBonus.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayoutBonusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, payoutBonus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payoutBonus))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayoutBonus in the database
        List<PayoutBonus> payoutBonusList = payoutBonusRepository.findAll();
        assertThat(payoutBonusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPayoutBonus() throws Exception {
        int databaseSizeBeforeUpdate = payoutBonusRepository.findAll().size();
        payoutBonus.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayoutBonusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payoutBonus))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayoutBonus in the database
        List<PayoutBonus> payoutBonusList = payoutBonusRepository.findAll();
        assertThat(payoutBonusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPayoutBonus() throws Exception {
        int databaseSizeBeforeUpdate = payoutBonusRepository.findAll().size();
        payoutBonus.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayoutBonusMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(payoutBonus))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PayoutBonus in the database
        List<PayoutBonus> payoutBonusList = payoutBonusRepository.findAll();
        assertThat(payoutBonusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePayoutBonus() throws Exception {
        // Initialize the database
        payoutBonusRepository.save(payoutBonus);

        int databaseSizeBeforeDelete = payoutBonusRepository.findAll().size();

        // Delete the payoutBonus
        restPayoutBonusMockMvc
            .perform(delete(ENTITY_API_URL_ID, payoutBonus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PayoutBonus> payoutBonusList = payoutBonusRepository.findAll();
        assertThat(payoutBonusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
