package com.datalinkedai.process.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.datalinkedai.process.IntegrationTest;
import com.datalinkedai.process.domain.Employee;
import com.datalinkedai.process.repository.EmployeeRepository;
import java.time.Instant;
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
 * Integration tests for the {@link EmployeeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeResourceIT {

    private static final Instant DEFAULT_DATE_OF_JOINING = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_JOINING = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EMPLOYEE_ID = "56043";
    private static final String UPDATED_EMPLOYEE_ID = "98822081";

    private static final String DEFAULT_ACCOUNT_ID = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_IFSC_CODE = "CBGI0FFQMTM";
    private static final String UPDATED_IFSC_CODE = "BTTO04ZRIDC";

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_HOLDER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_HOLDER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PF_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PF_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ESI_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ESI_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/employees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MockMvc restEmployeeMockMvc;

    private Employee employee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createEntity() {
        Employee employee = new Employee()
            .dateOfJoining(DEFAULT_DATE_OF_JOINING)
            .employeeId(DEFAULT_EMPLOYEE_ID)
            .accountId(DEFAULT_ACCOUNT_ID)
            .ifscCode(DEFAULT_IFSC_CODE)
            .bankName(DEFAULT_BANK_NAME)
            .bankHolderName(DEFAULT_BANK_HOLDER_NAME)
            .pfNumber(DEFAULT_PF_NUMBER)
            .esiNumber(DEFAULT_ESI_NUMBER);
        return employee;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createUpdatedEntity() {
        Employee employee = new Employee()
            .dateOfJoining(UPDATED_DATE_OF_JOINING)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .accountId(UPDATED_ACCOUNT_ID)
            .ifscCode(UPDATED_IFSC_CODE)
            .bankName(UPDATED_BANK_NAME)
            .bankHolderName(UPDATED_BANK_HOLDER_NAME)
            .pfNumber(UPDATED_PF_NUMBER)
            .esiNumber(UPDATED_ESI_NUMBER);
        return employee;
    }

    @BeforeEach
    public void initTest() {
        employeeRepository.deleteAll();
        employee = createEntity();
    }

    @Test
    void createEmployee() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();
        // Create the Employee
        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isCreated());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate + 1);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getDateOfJoining()).isEqualTo(DEFAULT_DATE_OF_JOINING);
        assertThat(testEmployee.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testEmployee.getAccountId()).isEqualTo(DEFAULT_ACCOUNT_ID);
        assertThat(testEmployee.getIfscCode()).isEqualTo(DEFAULT_IFSC_CODE);
        assertThat(testEmployee.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testEmployee.getBankHolderName()).isEqualTo(DEFAULT_BANK_HOLDER_NAME);
        assertThat(testEmployee.getPfNumber()).isEqualTo(DEFAULT_PF_NUMBER);
        assertThat(testEmployee.getEsiNumber()).isEqualTo(DEFAULT_ESI_NUMBER);
    }

    @Test
    void createEmployeeWithExistingId() throws Exception {
        // Create the Employee with an existing ID
        employee.setId("existing_id");

        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkDateOfJoiningIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setDateOfJoining(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllEmployees() throws Exception {
        // Initialize the database
        employeeRepository.save(employee);

        // Get all the employeeList
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId())))
            .andExpect(jsonPath("$.[*].dateOfJoining").value(hasItem(DEFAULT_DATE_OF_JOINING.toString())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID)))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID)))
            .andExpect(jsonPath("$.[*].ifscCode").value(hasItem(DEFAULT_IFSC_CODE)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].bankHolderName").value(hasItem(DEFAULT_BANK_HOLDER_NAME)))
            .andExpect(jsonPath("$.[*].pfNumber").value(hasItem(DEFAULT_PF_NUMBER)))
            .andExpect(jsonPath("$.[*].esiNumber").value(hasItem(DEFAULT_ESI_NUMBER)));
    }

    @Test
    void getEmployee() throws Exception {
        // Initialize the database
        employeeRepository.save(employee);

        // Get the employee
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL_ID, employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employee.getId()))
            .andExpect(jsonPath("$.dateOfJoining").value(DEFAULT_DATE_OF_JOINING.toString()))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID))
            .andExpect(jsonPath("$.accountId").value(DEFAULT_ACCOUNT_ID))
            .andExpect(jsonPath("$.ifscCode").value(DEFAULT_IFSC_CODE))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.bankHolderName").value(DEFAULT_BANK_HOLDER_NAME))
            .andExpect(jsonPath("$.pfNumber").value(DEFAULT_PF_NUMBER))
            .andExpect(jsonPath("$.esiNumber").value(DEFAULT_ESI_NUMBER));
    }

    @Test
    void getNonExistingEmployee() throws Exception {
        // Get the employee
        restEmployeeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewEmployee() throws Exception {
        // Initialize the database
        employeeRepository.save(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee
        Employee updatedEmployee = employeeRepository.findById(employee.getId()).get();
        updatedEmployee
            .dateOfJoining(UPDATED_DATE_OF_JOINING)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .accountId(UPDATED_ACCOUNT_ID)
            .ifscCode(UPDATED_IFSC_CODE)
            .bankName(UPDATED_BANK_NAME)
            .bankHolderName(UPDATED_BANK_HOLDER_NAME)
            .pfNumber(UPDATED_PF_NUMBER)
            .esiNumber(UPDATED_ESI_NUMBER);

        restEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmployee.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmployee))
            )
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getDateOfJoining()).isEqualTo(UPDATED_DATE_OF_JOINING);
        assertThat(testEmployee.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testEmployee.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testEmployee.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
        assertThat(testEmployee.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testEmployee.getBankHolderName()).isEqualTo(UPDATED_BANK_HOLDER_NAME);
        assertThat(testEmployee.getPfNumber()).isEqualTo(UPDATED_PF_NUMBER);
        assertThat(testEmployee.getEsiNumber()).isEqualTo(UPDATED_ESI_NUMBER);
    }

    @Test
    void putNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employee.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateEmployeeWithPatch() throws Exception {
        // Initialize the database
        employeeRepository.save(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee using partial update
        Employee partialUpdatedEmployee = new Employee();
        partialUpdatedEmployee.setId(employee.getId());

        partialUpdatedEmployee
            .dateOfJoining(UPDATED_DATE_OF_JOINING)
            .accountId(UPDATED_ACCOUNT_ID)
            .bankName(UPDATED_BANK_NAME)
            .bankHolderName(UPDATED_BANK_HOLDER_NAME);

        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployee))
            )
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getDateOfJoining()).isEqualTo(UPDATED_DATE_OF_JOINING);
        assertThat(testEmployee.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testEmployee.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testEmployee.getIfscCode()).isEqualTo(DEFAULT_IFSC_CODE);
        assertThat(testEmployee.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testEmployee.getBankHolderName()).isEqualTo(UPDATED_BANK_HOLDER_NAME);
        assertThat(testEmployee.getPfNumber()).isEqualTo(DEFAULT_PF_NUMBER);
        assertThat(testEmployee.getEsiNumber()).isEqualTo(DEFAULT_ESI_NUMBER);
    }

    @Test
    void fullUpdateEmployeeWithPatch() throws Exception {
        // Initialize the database
        employeeRepository.save(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee using partial update
        Employee partialUpdatedEmployee = new Employee();
        partialUpdatedEmployee.setId(employee.getId());

        partialUpdatedEmployee
            .dateOfJoining(UPDATED_DATE_OF_JOINING)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .accountId(UPDATED_ACCOUNT_ID)
            .ifscCode(UPDATED_IFSC_CODE)
            .bankName(UPDATED_BANK_NAME)
            .bankHolderName(UPDATED_BANK_HOLDER_NAME)
            .pfNumber(UPDATED_PF_NUMBER)
            .esiNumber(UPDATED_ESI_NUMBER);

        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployee))
            )
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getDateOfJoining()).isEqualTo(UPDATED_DATE_OF_JOINING);
        assertThat(testEmployee.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testEmployee.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testEmployee.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
        assertThat(testEmployee.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testEmployee.getBankHolderName()).isEqualTo(UPDATED_BANK_HOLDER_NAME);
        assertThat(testEmployee.getPfNumber()).isEqualTo(UPDATED_PF_NUMBER);
        assertThat(testEmployee.getEsiNumber()).isEqualTo(UPDATED_ESI_NUMBER);
    }

    @Test
    void patchNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteEmployee() throws Exception {
        // Initialize the database
        employeeRepository.save(employee);

        int databaseSizeBeforeDelete = employeeRepository.findAll().size();

        // Delete the employee
        restEmployeeMockMvc
            .perform(delete(ENTITY_API_URL_ID, employee.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
