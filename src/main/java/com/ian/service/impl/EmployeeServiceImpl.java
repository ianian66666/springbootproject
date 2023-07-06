package com.ian.service.impl;

import com.ian.entity.Employee;
import com.ian.entity.Result;
import com.ian.mapper.EmployeeMapper;
import com.ian.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Override
    public Employee login(Employee employee) {
      Employee emp=  employeeMapper.login(employee);
        return emp;
    }
}
