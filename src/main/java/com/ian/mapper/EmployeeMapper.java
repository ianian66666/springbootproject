package com.ian.mapper;

import com.ian.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    @Select("select *from employee where username=#{username} ")
    Employee login(Employee employee);

    @Insert("INSERT INTO employee(name, username, password, phone, sex, id_number, create_time, update_time, create_user, update_user) " +
            "values(#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void addemp(Employee employee);

    List<Employee> list(String name);

    void update(Employee employee);

    @Select("select * from employee where id=#{id}")
    Employee getById(Integer id);
}
