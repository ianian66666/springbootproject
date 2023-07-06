package com.ian.mapper;

import com.ian.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    @Select("select *from employee where username=#{username} ")
    Employee login(Employee employee);
}
