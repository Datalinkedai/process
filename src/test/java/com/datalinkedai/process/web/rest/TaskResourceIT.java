package com.datalinkedai.process.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.datalinkedai.process.IntegrationTest;
import com.datalinkedai.process.domain.Task;
import com.datalinkedai.process.domain.enumeration.HealthyTask;
import com.datalinkedai.process.domain.enumeration.Status;
import com.datalinkedai.process.repository.TaskRepository;
import java.time.Duration;
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
 * Integration tests for the {@link TaskResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaskResourceIT {

    private static final Integer DEFAULT_TOTAL_NO_OF_WORK = 1;
    private static final Integer UPDATED_TOTAL_NO_OF_WORK = 2;

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final HealthyTask DEFAULT_HEALTH = HealthyTask.SUCCESS;
    private static final HealthyTask UPDATED_HEALTH = HealthyTask.GOOD;

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    private static final String DEFAULT_WORK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_WORK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final Duration DEFAULT_TOTAL_DURATION = Duration.ofHours(6);
    private static final Duration UPDATED_TOTAL_DURATION = Duration.ofHours(12);

    private static final Duration DEFAULT_DONE_DURATION = Duration.ofHours(6);
    private static final Duration UPDATED_DONE_DURATION = Duration.ofHours(12);

    private static final Boolean DEFAULT_VERIFIED = false;
    private static final Boolean UPDATED_VERIFIED = true;

    private static final String DEFAULT_FREE_TEXT_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_TEXT_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_TEXT_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_TEXT_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_TEXT_3 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_TEXT_3 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_TEXT_4 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_TEXT_4 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_TEXT_5 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_TEXT_5 = "BBBBBBBBBB";

    private static final Instant DEFAULT_TASK_ASSIGNED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TASK_ASSIGNED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TASK_COMPLETED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TASK_COMPLETED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/tasks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MockMvc restTaskMockMvc;

    private Task task;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Task createEntity() {
        Task task = new Task()
            .totalNoOfWork(DEFAULT_TOTAL_NO_OF_WORK)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .health(DEFAULT_HEALTH)
            .status(DEFAULT_STATUS)
            .workName(DEFAULT_WORK_NAME)
            .remarks(DEFAULT_REMARKS)
            .totalDuration(DEFAULT_TOTAL_DURATION)
            .doneDuration(DEFAULT_DONE_DURATION)
            .verified(DEFAULT_VERIFIED)
            .freeText1(DEFAULT_FREE_TEXT_1)
            .freeText2(DEFAULT_FREE_TEXT_2)
            .freeText3(DEFAULT_FREE_TEXT_3)
            .freeText4(DEFAULT_FREE_TEXT_4)
            .freeText5(DEFAULT_FREE_TEXT_5)
            .taskAssignedDate(DEFAULT_TASK_ASSIGNED_DATE)
            .taskCompletedDate(DEFAULT_TASK_COMPLETED_DATE);
        return task;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Task createUpdatedEntity() {
        Task task = new Task()
            .totalNoOfWork(UPDATED_TOTAL_NO_OF_WORK)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .health(UPDATED_HEALTH)
            .status(UPDATED_STATUS)
            .workName(UPDATED_WORK_NAME)
            .remarks(UPDATED_REMARKS)
            .totalDuration(UPDATED_TOTAL_DURATION)
            .doneDuration(UPDATED_DONE_DURATION)
            .verified(UPDATED_VERIFIED)
            .freeText1(UPDATED_FREE_TEXT_1)
            .freeText2(UPDATED_FREE_TEXT_2)
            .freeText3(UPDATED_FREE_TEXT_3)
            .freeText4(UPDATED_FREE_TEXT_4)
            .freeText5(UPDATED_FREE_TEXT_5)
            .taskAssignedDate(UPDATED_TASK_ASSIGNED_DATE)
            .taskCompletedDate(UPDATED_TASK_COMPLETED_DATE);
        return task;
    }

    @BeforeEach
    public void initTest() {
        taskRepository.deleteAll();
        task = createEntity();
    }

    @Test
    void createTask() throws Exception {
        int databaseSizeBeforeCreate = taskRepository.findAll().size();
        // Create the Task
        restTaskMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(task)))
            .andExpect(status().isCreated());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeCreate + 1);
        Task testTask = taskList.get(taskList.size() - 1);
        assertThat(testTask.getTotalNoOfWork()).isEqualTo(DEFAULT_TOTAL_NO_OF_WORK);
        assertThat(testTask.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTask.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testTask.getHealth()).isEqualTo(DEFAULT_HEALTH);
        assertThat(testTask.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTask.getWorkName()).isEqualTo(DEFAULT_WORK_NAME);
        assertThat(testTask.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testTask.getTotalDuration()).isEqualTo(DEFAULT_TOTAL_DURATION);
        assertThat(testTask.getDoneDuration()).isEqualTo(DEFAULT_DONE_DURATION);
        assertThat(testTask.getVerified()).isEqualTo(DEFAULT_VERIFIED);
        assertThat(testTask.getFreeText1()).isEqualTo(DEFAULT_FREE_TEXT_1);
        assertThat(testTask.getFreeText2()).isEqualTo(DEFAULT_FREE_TEXT_2);
        assertThat(testTask.getFreeText3()).isEqualTo(DEFAULT_FREE_TEXT_3);
        assertThat(testTask.getFreeText4()).isEqualTo(DEFAULT_FREE_TEXT_4);
        assertThat(testTask.getFreeText5()).isEqualTo(DEFAULT_FREE_TEXT_5);
        assertThat(testTask.getTaskAssignedDate()).isEqualTo(DEFAULT_TASK_ASSIGNED_DATE);
        assertThat(testTask.getTaskCompletedDate()).isEqualTo(DEFAULT_TASK_COMPLETED_DATE);
    }

    @Test
    void createTaskWithExistingId() throws Exception {
        // Create the Task with an existing ID
        task.setId("existing_id");

        int databaseSizeBeforeCreate = taskRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(task)))
            .andExpect(status().isBadRequest());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllTasks() throws Exception {
        // Initialize the database
        taskRepository.save(task);

        // Get all the taskList
        restTaskMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(task.getId())))
            .andExpect(jsonPath("$.[*].totalNoOfWork").value(hasItem(DEFAULT_TOTAL_NO_OF_WORK)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].health").value(hasItem(DEFAULT_HEALTH.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].workName").value(hasItem(DEFAULT_WORK_NAME)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].totalDuration").value(hasItem(DEFAULT_TOTAL_DURATION.toString())))
            .andExpect(jsonPath("$.[*].doneDuration").value(hasItem(DEFAULT_DONE_DURATION.toString())))
            .andExpect(jsonPath("$.[*].verified").value(hasItem(DEFAULT_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeText1").value(hasItem(DEFAULT_FREE_TEXT_1)))
            .andExpect(jsonPath("$.[*].freeText2").value(hasItem(DEFAULT_FREE_TEXT_2)))
            .andExpect(jsonPath("$.[*].freeText3").value(hasItem(DEFAULT_FREE_TEXT_3)))
            .andExpect(jsonPath("$.[*].freeText4").value(hasItem(DEFAULT_FREE_TEXT_4)))
            .andExpect(jsonPath("$.[*].freeText5").value(hasItem(DEFAULT_FREE_TEXT_5)))
            .andExpect(jsonPath("$.[*].taskAssignedDate").value(hasItem(DEFAULT_TASK_ASSIGNED_DATE.toString())))
            .andExpect(jsonPath("$.[*].taskCompletedDate").value(hasItem(DEFAULT_TASK_COMPLETED_DATE.toString())));
    }

    @Test
    void getTask() throws Exception {
        // Initialize the database
        taskRepository.save(task);

        // Get the task
        restTaskMockMvc
            .perform(get(ENTITY_API_URL_ID, task.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(task.getId()))
            .andExpect(jsonPath("$.totalNoOfWork").value(DEFAULT_TOTAL_NO_OF_WORK))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.health").value(DEFAULT_HEALTH.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.workName").value(DEFAULT_WORK_NAME))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS))
            .andExpect(jsonPath("$.totalDuration").value(DEFAULT_TOTAL_DURATION.toString()))
            .andExpect(jsonPath("$.doneDuration").value(DEFAULT_DONE_DURATION.toString()))
            .andExpect(jsonPath("$.verified").value(DEFAULT_VERIFIED.booleanValue()))
            .andExpect(jsonPath("$.freeText1").value(DEFAULT_FREE_TEXT_1))
            .andExpect(jsonPath("$.freeText2").value(DEFAULT_FREE_TEXT_2))
            .andExpect(jsonPath("$.freeText3").value(DEFAULT_FREE_TEXT_3))
            .andExpect(jsonPath("$.freeText4").value(DEFAULT_FREE_TEXT_4))
            .andExpect(jsonPath("$.freeText5").value(DEFAULT_FREE_TEXT_5))
            .andExpect(jsonPath("$.taskAssignedDate").value(DEFAULT_TASK_ASSIGNED_DATE.toString()))
            .andExpect(jsonPath("$.taskCompletedDate").value(DEFAULT_TASK_COMPLETED_DATE.toString()));
    }

    @Test
    void getNonExistingTask() throws Exception {
        // Get the task
        restTaskMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewTask() throws Exception {
        // Initialize the database
        taskRepository.save(task);

        int databaseSizeBeforeUpdate = taskRepository.findAll().size();

        // Update the task
        Task updatedTask = taskRepository.findById(task.getId()).get();
        updatedTask
            .totalNoOfWork(UPDATED_TOTAL_NO_OF_WORK)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .health(UPDATED_HEALTH)
            .status(UPDATED_STATUS)
            .workName(UPDATED_WORK_NAME)
            .remarks(UPDATED_REMARKS)
            .totalDuration(UPDATED_TOTAL_DURATION)
            .doneDuration(UPDATED_DONE_DURATION)
            .verified(UPDATED_VERIFIED)
            .freeText1(UPDATED_FREE_TEXT_1)
            .freeText2(UPDATED_FREE_TEXT_2)
            .freeText3(UPDATED_FREE_TEXT_3)
            .freeText4(UPDATED_FREE_TEXT_4)
            .freeText5(UPDATED_FREE_TEXT_5)
            .taskAssignedDate(UPDATED_TASK_ASSIGNED_DATE)
            .taskCompletedDate(UPDATED_TASK_COMPLETED_DATE);

        restTaskMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTask.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTask))
            )
            .andExpect(status().isOk());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
        Task testTask = taskList.get(taskList.size() - 1);
        assertThat(testTask.getTotalNoOfWork()).isEqualTo(UPDATED_TOTAL_NO_OF_WORK);
        assertThat(testTask.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTask.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTask.getHealth()).isEqualTo(UPDATED_HEALTH);
        assertThat(testTask.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTask.getWorkName()).isEqualTo(UPDATED_WORK_NAME);
        assertThat(testTask.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testTask.getTotalDuration()).isEqualTo(UPDATED_TOTAL_DURATION);
        assertThat(testTask.getDoneDuration()).isEqualTo(UPDATED_DONE_DURATION);
        assertThat(testTask.getVerified()).isEqualTo(UPDATED_VERIFIED);
        assertThat(testTask.getFreeText1()).isEqualTo(UPDATED_FREE_TEXT_1);
        assertThat(testTask.getFreeText2()).isEqualTo(UPDATED_FREE_TEXT_2);
        assertThat(testTask.getFreeText3()).isEqualTo(UPDATED_FREE_TEXT_3);
        assertThat(testTask.getFreeText4()).isEqualTo(UPDATED_FREE_TEXT_4);
        assertThat(testTask.getFreeText5()).isEqualTo(UPDATED_FREE_TEXT_5);
        assertThat(testTask.getTaskAssignedDate()).isEqualTo(UPDATED_TASK_ASSIGNED_DATE);
        assertThat(testTask.getTaskCompletedDate()).isEqualTo(UPDATED_TASK_COMPLETED_DATE);
    }

    @Test
    void putNonExistingTask() throws Exception {
        int databaseSizeBeforeUpdate = taskRepository.findAll().size();
        task.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskMockMvc
            .perform(
                put(ENTITY_API_URL_ID, task.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(task))
            )
            .andExpect(status().isBadRequest());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchTask() throws Exception {
        int databaseSizeBeforeUpdate = taskRepository.findAll().size();
        task.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(task))
            )
            .andExpect(status().isBadRequest());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamTask() throws Exception {
        int databaseSizeBeforeUpdate = taskRepository.findAll().size();
        task.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(task)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateTaskWithPatch() throws Exception {
        // Initialize the database
        taskRepository.save(task);

        int databaseSizeBeforeUpdate = taskRepository.findAll().size();

        // Update the task using partial update
        Task partialUpdatedTask = new Task();
        partialUpdatedTask.setId(task.getId());

        partialUpdatedTask
            .startDate(UPDATED_START_DATE)
            .health(UPDATED_HEALTH)
            .totalDuration(UPDATED_TOTAL_DURATION)
            .doneDuration(UPDATED_DONE_DURATION)
            .verified(UPDATED_VERIFIED)
            .freeText2(UPDATED_FREE_TEXT_2)
            .freeText5(UPDATED_FREE_TEXT_5)
            .taskAssignedDate(UPDATED_TASK_ASSIGNED_DATE)
            .taskCompletedDate(UPDATED_TASK_COMPLETED_DATE);

        restTaskMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTask.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTask))
            )
            .andExpect(status().isOk());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
        Task testTask = taskList.get(taskList.size() - 1);
        assertThat(testTask.getTotalNoOfWork()).isEqualTo(DEFAULT_TOTAL_NO_OF_WORK);
        assertThat(testTask.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTask.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testTask.getHealth()).isEqualTo(UPDATED_HEALTH);
        assertThat(testTask.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTask.getWorkName()).isEqualTo(DEFAULT_WORK_NAME);
        assertThat(testTask.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testTask.getTotalDuration()).isEqualTo(UPDATED_TOTAL_DURATION);
        assertThat(testTask.getDoneDuration()).isEqualTo(UPDATED_DONE_DURATION);
        assertThat(testTask.getVerified()).isEqualTo(UPDATED_VERIFIED);
        assertThat(testTask.getFreeText1()).isEqualTo(DEFAULT_FREE_TEXT_1);
        assertThat(testTask.getFreeText2()).isEqualTo(UPDATED_FREE_TEXT_2);
        assertThat(testTask.getFreeText3()).isEqualTo(DEFAULT_FREE_TEXT_3);
        assertThat(testTask.getFreeText4()).isEqualTo(DEFAULT_FREE_TEXT_4);
        assertThat(testTask.getFreeText5()).isEqualTo(UPDATED_FREE_TEXT_5);
        assertThat(testTask.getTaskAssignedDate()).isEqualTo(UPDATED_TASK_ASSIGNED_DATE);
        assertThat(testTask.getTaskCompletedDate()).isEqualTo(UPDATED_TASK_COMPLETED_DATE);
    }

    @Test
    void fullUpdateTaskWithPatch() throws Exception {
        // Initialize the database
        taskRepository.save(task);

        int databaseSizeBeforeUpdate = taskRepository.findAll().size();

        // Update the task using partial update
        Task partialUpdatedTask = new Task();
        partialUpdatedTask.setId(task.getId());

        partialUpdatedTask
            .totalNoOfWork(UPDATED_TOTAL_NO_OF_WORK)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .health(UPDATED_HEALTH)
            .status(UPDATED_STATUS)
            .workName(UPDATED_WORK_NAME)
            .remarks(UPDATED_REMARKS)
            .totalDuration(UPDATED_TOTAL_DURATION)
            .doneDuration(UPDATED_DONE_DURATION)
            .verified(UPDATED_VERIFIED)
            .freeText1(UPDATED_FREE_TEXT_1)
            .freeText2(UPDATED_FREE_TEXT_2)
            .freeText3(UPDATED_FREE_TEXT_3)
            .freeText4(UPDATED_FREE_TEXT_4)
            .freeText5(UPDATED_FREE_TEXT_5)
            .taskAssignedDate(UPDATED_TASK_ASSIGNED_DATE)
            .taskCompletedDate(UPDATED_TASK_COMPLETED_DATE);

        restTaskMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTask.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTask))
            )
            .andExpect(status().isOk());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
        Task testTask = taskList.get(taskList.size() - 1);
        assertThat(testTask.getTotalNoOfWork()).isEqualTo(UPDATED_TOTAL_NO_OF_WORK);
        assertThat(testTask.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTask.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTask.getHealth()).isEqualTo(UPDATED_HEALTH);
        assertThat(testTask.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTask.getWorkName()).isEqualTo(UPDATED_WORK_NAME);
        assertThat(testTask.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testTask.getTotalDuration()).isEqualTo(UPDATED_TOTAL_DURATION);
        assertThat(testTask.getDoneDuration()).isEqualTo(UPDATED_DONE_DURATION);
        assertThat(testTask.getVerified()).isEqualTo(UPDATED_VERIFIED);
        assertThat(testTask.getFreeText1()).isEqualTo(UPDATED_FREE_TEXT_1);
        assertThat(testTask.getFreeText2()).isEqualTo(UPDATED_FREE_TEXT_2);
        assertThat(testTask.getFreeText3()).isEqualTo(UPDATED_FREE_TEXT_3);
        assertThat(testTask.getFreeText4()).isEqualTo(UPDATED_FREE_TEXT_4);
        assertThat(testTask.getFreeText5()).isEqualTo(UPDATED_FREE_TEXT_5);
        assertThat(testTask.getTaskAssignedDate()).isEqualTo(UPDATED_TASK_ASSIGNED_DATE);
        assertThat(testTask.getTaskCompletedDate()).isEqualTo(UPDATED_TASK_COMPLETED_DATE);
    }

    @Test
    void patchNonExistingTask() throws Exception {
        int databaseSizeBeforeUpdate = taskRepository.findAll().size();
        task.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, task.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(task))
            )
            .andExpect(status().isBadRequest());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchTask() throws Exception {
        int databaseSizeBeforeUpdate = taskRepository.findAll().size();
        task.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(task))
            )
            .andExpect(status().isBadRequest());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamTask() throws Exception {
        int databaseSizeBeforeUpdate = taskRepository.findAll().size();
        task.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(task)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteTask() throws Exception {
        // Initialize the database
        taskRepository.save(task);

        int databaseSizeBeforeDelete = taskRepository.findAll().size();

        // Delete the task
        restTaskMockMvc
            .perform(delete(ENTITY_API_URL_ID, task.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
