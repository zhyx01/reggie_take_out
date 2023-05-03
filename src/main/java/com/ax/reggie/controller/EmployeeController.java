package com.ax.reggie.controller;

import com.ax.reggie.common.R;
import com.ax.reggie.entity.Employee;
import com.ax.reggie.service.EmployeeService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * className: EmployeeController
 * description: 员工管理
 *
 * @author: axiang
 * date: 2023/5/1 0001 10:39
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    /**
     * description: 员工后台登录
     * @param request:
     * @param employee: 封装账号密码
     * @return: R<Employee> <br>
     * date: 2023/5/1 0001 <br>
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        return employeeService.login(request, employee);
    }

    /**
     * description: 注销登录
     * @param request:
     * @return: R <br>
     * date: 2023/5/1 0001 <br>
     */
    @PostMapping("/logout")
    public R logout(HttpServletRequest request) {
        // 1. 删除session中存入的用户id
        request.removeAttribute("employee");
        // 返回提示信息
        return  R.success("注销成功");
    }

    /**
     * description: 新增员工
     * @param request:
     * @param employee: 封装页面提交的数据
     * @return: R<String> <br>
     * date: 2023/5/1 0001 <br>
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工, 员工信息: {}", employee.toString());

        return employeeService.addEmployeeInfo(request, employee);
    }

    /**
     * description: 分页查询员工信息
     * @param page: 当前页码
     * @param pageSize: 每页显示大小
     * @param name: 根据姓名模糊查询
     * @return: R<Page> <br>
     * date: 2023/5/1 0001 <br>
     */
    @GetMapping("/page")
    public R<Page> getUserByPage(int page, int pageSize, String name) {
        log.info("分页查询员工信息, 当前页: {}, 大小: {}, 姓名: {}", page, pageSize, name);
        return employeeService.getUserByPage(page, pageSize, name);
    }

    /**
     * description:修改员工信息: 编辑/状态
     * @param request:
     * @param employee: 封装修改的数据
     * @return: R<String> <br>
     * date: 2023/5/1 0001 <br>
     */
    @PutMapping
    public R<String> updateEmployeeInfo(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("页面传入的数据: {}", employee.toString());

        long id = Thread.currentThread().getId();
        log.info("当前线程id为: {}", id);

        return employeeService.updateEmployeeInfo(request, employee);
    }

    /**
     * description: 编辑员工信息查询回显
     * @param id: 员工id
     * @return: R<Employee> <br>
     * date: 2023/5/1 0001 <br>
     */
    @GetMapping("/{id}")
    public R<Employee> getEmployeeById(@PathVariable Long id) {
        log.info("根据员工id查询信息回显, id: {}", id);
        return employeeService.getEmployeeById(id);
    }
}
