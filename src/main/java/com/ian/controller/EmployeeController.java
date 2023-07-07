package com.ian.controller;

import com.ian.entity.Employee;
import com.ian.entity.PageBean;
import com.ian.entity.Result;
import com.ian.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;


    /**
     * 員工登入
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public Result<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        //將員工登入的密碼進行MD5加密
        String pwd = employee.getPassword();
        pwd = DigestUtils.md5DigestAsHex(pwd.getBytes());
        Employee emp = employeeService.login(employee);

        if (emp == null) {
            return Result.error("登入失敗");
        }
        if (!emp.getPassword().equals(pwd)) {
            return Result.error("密碼錯誤");
        }
        if (emp.getStatus() == 0) {
            return Result.error("帳號已停用");
        }
//           登入成功後將id存入session中並返回登入成功結果
        request.getSession().setAttribute("employee", emp.getId());
        return Result.success(emp);
    }

    /**
     * 員工登出
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
        //清理Seesion中保存的當前登入員工的id
        request.getSession().removeAttribute("employee");
        return Result.success("退出成功");

    }

    /**
     * 新增員工
     *
     * @param employee
     * @return
     */
    @PostMapping
    public Result<String> addemp(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增的員工信息：{}", employee.toString());

        employeeService.addemp(request, employee);

        return Result.success("新增成功");
    }

    /**
     * 員工列表分頁查詢
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result<PageBean> page(int page, int pageSize, String name) {
        log.info("{},{},{}", page, pageSize, name);
        PageBean pageBean = employeeService.page(page, pageSize, name);
        return Result.success(pageBean);
    }


}
