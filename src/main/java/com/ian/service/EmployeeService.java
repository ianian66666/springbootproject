package com.ian.service;

import com.ian.entity.Employee;
import com.ian.entity.PageBean;
import jakarta.servlet.http.HttpServletRequest;

public interface EmployeeService {
    Employee login(Employee employee);

    void addemp(HttpServletRequest request,Employee employee);

    PageBean page(int page, int pageSize, String name);
}
