package com.datalinkedai.process.service.impl;

import com.datalinkedai.process.domain.Task;
import com.datalinkedai.process.repository.TaskRepository;
import com.datalinkedai.process.service.TaskService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Task}.
 */
@Service
public class TaskServiceImpl implements TaskService {

    private final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task save(Task task) {
        log.debug("Request to save Task : {}", task);
        return taskRepository.save(task);
    }

    @Override
    public Task update(Task task) {
        log.debug("Request to save Task : {}", task);
        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> partialUpdate(Task task) {
        log.debug("Request to partially update Task : {}", task);

        return taskRepository
            .findById(task.getId())
            .map(existingTask -> {
                if (task.getTotalNoOfWork() != null) {
                    existingTask.setTotalNoOfWork(task.getTotalNoOfWork());
                }
                if (task.getStartDate() != null) {
                    existingTask.setStartDate(task.getStartDate());
                }
                if (task.getEndDate() != null) {
                    existingTask.setEndDate(task.getEndDate());
                }
                if (task.getHealth() != null) {
                    existingTask.setHealth(task.getHealth());
                }
                if (task.getStatus() != null) {
                    existingTask.setStatus(task.getStatus());
                }
                if (task.getWorkName() != null) {
                    existingTask.setWorkName(task.getWorkName());
                }
                if (task.getRemarks() != null) {
                    existingTask.setRemarks(task.getRemarks());
                }
                if (task.getTotalDuration() != null) {
                    existingTask.setTotalDuration(task.getTotalDuration());
                }
                if (task.getDoneDuration() != null) {
                    existingTask.setDoneDuration(task.getDoneDuration());
                }
                if (task.getVerified() != null) {
                    existingTask.setVerified(task.getVerified());
                }
                if (task.getFreeText1() != null) {
                    existingTask.setFreeText1(task.getFreeText1());
                }
                if (task.getFreeText2() != null) {
                    existingTask.setFreeText2(task.getFreeText2());
                }
                if (task.getFreeText3() != null) {
                    existingTask.setFreeText3(task.getFreeText3());
                }
                if (task.getFreeText4() != null) {
                    existingTask.setFreeText4(task.getFreeText4());
                }
                if (task.getFreeText5() != null) {
                    existingTask.setFreeText5(task.getFreeText5());
                }
                if (task.getTaskAssignedDate() != null) {
                    existingTask.setTaskAssignedDate(task.getTaskAssignedDate());
                }
                if (task.getTaskCompletedDate() != null) {
                    existingTask.setTaskCompletedDate(task.getTaskCompletedDate());
                }

                return existingTask;
            })
            .map(taskRepository::save);
    }

    @Override
    public List<Task> findAll() {
        log.debug("Request to get all Tasks");
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> findOne(String id) {
        log.debug("Request to get Task : {}", id);
        return taskRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Task : {}", id);
        taskRepository.deleteById(id);
    }
}
