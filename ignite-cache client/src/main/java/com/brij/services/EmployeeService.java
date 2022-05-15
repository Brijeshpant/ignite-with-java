package com.brij.services;

//import com.brij.config.IgniteConfig;

import com.brij.dtos.EmployeeDto;
import com.brij.entities.Employee;
import com.brij.repositories.EmployeeRepository;
import org.apache.ignite.IgniteCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.cache.Cache;
import java.util.Objects;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public EmployeeDto getEmployee(int id) {

        Employee byId = employeeRepository.findById(id);
        return Objects.isNull(byId)
                ? null : new EmployeeDto(byId.getId(), byId.getName(), byId.getEmail());
    }

    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee entity = new Employee(employeeDto.getId(), employeeDto.getName(), employeeDto.getEmail());
        try {
            employeeRepository.save(employeeDto.getId(), entity);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to create employee");
        }
        return employeeDto;
    }
}
