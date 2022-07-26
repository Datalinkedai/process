package com.datalinkedai.process.service;

import com.datalinkedai.process.domain.Employee;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Employee}.
 */
public interface EmployeeService {
    /**
     * Save a employee.
     *
     * @param employee the entity to save.
     * @return the persisted entity.
     */
    Employee save(Employee employee);

    /**
     * Updates a employee.
     *
     * @param employee the entity to update.
     * @return the persisted entity.
     */
    Employee update(Employee employee);

    /**
     * Partially updates a employee.
     *
     * @param employee the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Employee> partialUpdate(Employee employee);

    /**
     * Get all the employees.
     *
     * @return the list of entities.
     */
    List<Employee> findAll();

    /**
     * Get the "id" employee.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Employee> findOne(String id);

    /**
     * Delete the "id" employee.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
