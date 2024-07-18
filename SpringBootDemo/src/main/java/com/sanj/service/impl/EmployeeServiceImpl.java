package com.sanj.service.impl;

import com.sanj.model.Employee;
import com.sanj.repository.EmployeeRepository;
import com.sanj.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public abstract class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee createEmoloyee(Employee employee){
        return employeeRepository.createEmployee(employee);
    }

}
