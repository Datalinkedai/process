package com.datalinkedai.process.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.datalinkedai.process.IntegrationTest;
import com.datalinkedai.process.domain.Client;
import com.datalinkedai.process.domain.enumeration.PayoutStructure;
import com.datalinkedai.process.domain.enumeration.Status;
import com.datalinkedai.process.domain.enumeration.TaskType;
import com.datalinkedai.process.repository.ClientRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link ClientResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClientResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BUSINESS_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BUSINESS_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_BUSINESS_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BUSINESS_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final PayoutStructure DEFAULT_PAYOUT = PayoutStructure.MONTH;
    private static final PayoutStructure UPDATED_PAYOUT = PayoutStructure.WEEK;

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final String DEFAULT_TASK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TASK_NAME = "BBBBBBBBBB";

    private static final TaskType DEFAULT_TASK_TYPE = TaskType.FILE;
    private static final TaskType UPDATED_TASK_TYPE = TaskType.COUNT;

    private static final String ENTITY_API_URL = "/api/clients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private MockMvc restClientMockMvc;

    private Client client;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createEntity() {
        Client client = new Client()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .description(DEFAULT_DESCRIPTION)
            .businessStartDate(DEFAULT_BUSINESS_START_DATE)
            .businessEndDate(DEFAULT_BUSINESS_END_DATE)
            .payout(DEFAULT_PAYOUT)
            .status(DEFAULT_STATUS)
            .remarks(DEFAULT_REMARKS)
            .taskName(DEFAULT_TASK_NAME)
            .taskType(DEFAULT_TASK_TYPE);
        return client;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createUpdatedEntity() {
        Client client = new Client()
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .description(UPDATED_DESCRIPTION)
            .businessStartDate(UPDATED_BUSINESS_START_DATE)
            .businessEndDate(UPDATED_BUSINESS_END_DATE)
            .payout(UPDATED_PAYOUT)
            .status(UPDATED_STATUS)
            .remarks(UPDATED_REMARKS)
            .taskName(UPDATED_TASK_NAME)
            .taskType(UPDATED_TASK_TYPE);
        return client;
    }

    @BeforeEach
    public void initTest() {
        clientRepository.deleteAll();
        client = createEntity();
    }

    @Test
    void createClient() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();
        // Create the Client
        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isCreated());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate + 1);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClient.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testClient.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testClient.getBusinessStartDate()).isEqualTo(DEFAULT_BUSINESS_START_DATE);
        assertThat(testClient.getBusinessEndDate()).isEqualTo(DEFAULT_BUSINESS_END_DATE);
        assertThat(testClient.getPayout()).isEqualTo(DEFAULT_PAYOUT);
        assertThat(testClient.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testClient.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testClient.getTaskName()).isEqualTo(DEFAULT_TASK_NAME);
        assertThat(testClient.getTaskType()).isEqualTo(DEFAULT_TASK_TYPE);
    }

    @Test
    void createClientWithExistingId() throws Exception {
        // Create the Client with an existing ID
        client.setId("existing_id");

        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setName(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllClients() throws Exception {
        // Initialize the database
        clientRepository.save(client);

        // Get all the clientList
        restClientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].businessStartDate").value(hasItem(DEFAULT_BUSINESS_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].businessEndDate").value(hasItem(DEFAULT_BUSINESS_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].payout").value(hasItem(DEFAULT_PAYOUT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].taskName").value(hasItem(DEFAULT_TASK_NAME)))
            .andExpect(jsonPath("$.[*].taskType").value(hasItem(DEFAULT_TASK_TYPE.toString())));
    }

    @Test
    void getClient() throws Exception {
        // Initialize the database
        clientRepository.save(client);

        // Get the client
        restClientMockMvc
            .perform(get(ENTITY_API_URL_ID, client.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(client.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.businessStartDate").value(DEFAULT_BUSINESS_START_DATE.toString()))
            .andExpect(jsonPath("$.businessEndDate").value(DEFAULT_BUSINESS_END_DATE.toString()))
            .andExpect(jsonPath("$.payout").value(DEFAULT_PAYOUT.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS))
            .andExpect(jsonPath("$.taskName").value(DEFAULT_TASK_NAME))
            .andExpect(jsonPath("$.taskType").value(DEFAULT_TASK_TYPE.toString()));
    }

    @Test
    void getNonExistingClient() throws Exception {
        // Get the client
        restClientMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewClient() throws Exception {
        // Initialize the database
        clientRepository.save(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client
        Client updatedClient = clientRepository.findById(client.getId()).get();
        updatedClient
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .description(UPDATED_DESCRIPTION)
            .businessStartDate(UPDATED_BUSINESS_START_DATE)
            .businessEndDate(UPDATED_BUSINESS_END_DATE)
            .payout(UPDATED_PAYOUT)
            .status(UPDATED_STATUS)
            .remarks(UPDATED_REMARKS)
            .taskName(UPDATED_TASK_NAME)
            .taskType(UPDATED_TASK_TYPE);

        restClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClient.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClient))
            )
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClient.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testClient.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClient.getBusinessStartDate()).isEqualTo(UPDATED_BUSINESS_START_DATE);
        assertThat(testClient.getBusinessEndDate()).isEqualTo(UPDATED_BUSINESS_END_DATE);
        assertThat(testClient.getPayout()).isEqualTo(UPDATED_PAYOUT);
        assertThat(testClient.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testClient.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testClient.getTaskName()).isEqualTo(UPDATED_TASK_NAME);
        assertThat(testClient.getTaskType()).isEqualTo(UPDATED_TASK_TYPE);
    }

    @Test
    void putNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, client.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(client))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(client))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateClientWithPatch() throws Exception {
        // Initialize the database
        clientRepository.save(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client using partial update
        Client partialUpdatedClient = new Client();
        partialUpdatedClient.setId(client.getId());

        partialUpdatedClient
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .taskName(UPDATED_TASK_NAME)
            .taskType(UPDATED_TASK_TYPE);

        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClient))
            )
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClient.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testClient.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClient.getBusinessStartDate()).isEqualTo(DEFAULT_BUSINESS_START_DATE);
        assertThat(testClient.getBusinessEndDate()).isEqualTo(DEFAULT_BUSINESS_END_DATE);
        assertThat(testClient.getPayout()).isEqualTo(DEFAULT_PAYOUT);
        assertThat(testClient.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testClient.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testClient.getTaskName()).isEqualTo(UPDATED_TASK_NAME);
        assertThat(testClient.getTaskType()).isEqualTo(UPDATED_TASK_TYPE);
    }

    @Test
    void fullUpdateClientWithPatch() throws Exception {
        // Initialize the database
        clientRepository.save(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client using partial update
        Client partialUpdatedClient = new Client();
        partialUpdatedClient.setId(client.getId());

        partialUpdatedClient
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .description(UPDATED_DESCRIPTION)
            .businessStartDate(UPDATED_BUSINESS_START_DATE)
            .businessEndDate(UPDATED_BUSINESS_END_DATE)
            .payout(UPDATED_PAYOUT)
            .status(UPDATED_STATUS)
            .remarks(UPDATED_REMARKS)
            .taskName(UPDATED_TASK_NAME)
            .taskType(UPDATED_TASK_TYPE);

        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClient))
            )
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClient.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testClient.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClient.getBusinessStartDate()).isEqualTo(UPDATED_BUSINESS_START_DATE);
        assertThat(testClient.getBusinessEndDate()).isEqualTo(UPDATED_BUSINESS_END_DATE);
        assertThat(testClient.getPayout()).isEqualTo(UPDATED_PAYOUT);
        assertThat(testClient.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testClient.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testClient.getTaskName()).isEqualTo(UPDATED_TASK_NAME);
        assertThat(testClient.getTaskType()).isEqualTo(UPDATED_TASK_TYPE);
    }

    @Test
    void patchNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, client.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(client))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(client))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteClient() throws Exception {
        // Initialize the database
        clientRepository.save(client);

        int databaseSizeBeforeDelete = clientRepository.findAll().size();

        // Delete the client
        restClientMockMvc
            .perform(delete(ENTITY_API_URL_ID, client.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
