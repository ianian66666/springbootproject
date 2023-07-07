package com.ian.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ian.entity.Employee;
import com.ian.entity.PageBean;
import com.ian.entity.Result;
import com.ian.mapper.EmployeeMapper;
import com.ian.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import java.util.List;
import java.time.LocalDateTime;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Override
    public Employee login(Employee employee) {
      Employee emp=  employeeMapper.login(employee);
        return emp;
    }

    @Override
    public void addemp(HttpServletRequest request, Employee employee) {
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        Long empId  = (Long)request.getSession().getAttribute("employee");
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);

        employeeMapper.addemp(employee);


    }

    @Override
    public PageBean page(int page, int pageSize, String name) {
        //1.設置分頁參數
        PageHelper.startPage(page,pageSize);
        //2.執行查詢
        List<Employee> empList = employeeMapper.list(name);
        Page<Employee> p = (Page<Employee>)empList;
        //封裝pageBean對象
        PageBean pageBean = new PageBean(p.getTotal(),p.getResult());
        return   pageBean   ;
    }
}
