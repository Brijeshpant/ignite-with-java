package com.brij.controllers;

import com.brij.dtos.EmployeeDto;
import com.brij.entities.Employee;
import com.brij.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    EmployeeService service;

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable int id) {
        EmployeeDto employee = service.getEmployee(id);
        if (Objects.isNull(employee)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employee);
    }

    @PostMapping()
    public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto) {
        return service.createEmployee(employeeDto);
    }


}
