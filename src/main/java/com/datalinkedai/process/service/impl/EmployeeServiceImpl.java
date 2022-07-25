package com.datalinkedai.process.service.impl;

import com.datalinkedai.process.domain.Employee;
import com.datalinkedai.process.repository.EmployeeRepository;
import com.datalinkedai.process.service.EmployeeService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Employee}.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee save(Employee employee) {
        log.debug("Request to save Employee : {}", employee);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Employee employee) {
        log.debug("Request to save Employee : {}", employee);
        return employeeRepository.save(employee);
    }

    @Override
    public Optional<Employee> partialUpdate(Employee employee) {
        log.debug("Request to partially update Employee : {}", employee);

        return employeeRepository
            .findById(employee.getId())
            .map(existingEmployee -> {
                if (employee.getDateOfJoining() != null) {
                    existingEmployee.setDateOfJoining(employee.getDateOfJoining());
                }
                if (employee.getEmployeeId() != null) {
                    existingEmployee.setEmployeeId(employee.getEmployeeId());
                }
                if (employee.getAccountId() != null) {
                    existingEmployee.setAccountId(employee.getAccountId());
                }
                if (employee.getIfscCode() != null) {
                    existingEmployee.setIfscCode(employee.getIfscCode());
                }
                if (employee.getBankName() != null) {
                    existingEmployee.setBankName(employee.getBankName());
                }
                if (employee.getBankHolderName() != null) {
                    existingEmployee.setBankHolderName(employee.getBankHolderName());
                }
                if (employee.getPfNumber() != null) {
                    existingEmployee.setPfNumber(employee.getPfNumber());
                }
                if (employee.getEsiNumber() != null) {
                    existingEmployee.setEsiNumber(employee.getEsiNumber());
                }

                return existingEmployee;
            })
            .map(employeeRepository::save);
    }

    @Override
    public List<Employee> findAll() {
        log.debug("Request to get all Employees");
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> findOne(String id) {
        log.debug("Request to get Employee : {}", id);
        return employeeRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
    }
}
