package com.ax.reggie.service;

import com.ax.reggie.common.R;
import com.ax.reggie.entity.Employee;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

public interface EmployeeService extends IService<Employee> {

    /**
     * description: 员工后台系统登录
     *
     * @param request: 存登录成功用户id
     * @param employee : 封装账号密码
     * @return: R<Employee> <br>
     * date: 2023/5/1 0001 <br>
     */
    R<Employee> login(HttpServletRequest request, Employee employee);

    /**
     * description: 新增员工
     * @param request:
     * @param employee:
     * @return: R<String> <br>
     * date: 2023/5/1 0001 <br>
     */
    R<String> addEmployeeInfo(HttpServletRequest request, Employee employee);

    /**
     * description: 分页查询员工信息
     * @param page: 当前页码
     * @param pageSize: 每页显示大小
     * @param name: 根据姓名模糊查询
     * @return: R<Page> <br>
     * date: 2023/5/1 0001 <br>
     */
    R<Page> getUserByPage(int page, int pageSize, String name);

    /**
     * description:修改员工信息: 编辑/状态
     * @param request:
     * @param employee: 封装修改的数据
     * @return: R<String> <br>
     * date: 2023/5/1 0001 <br>
     */
    R<String> updateEmployeeInfo(HttpServletRequest request, Employee employee);

    /**
     * description: 编辑员工信息查询回显
     * @param id: 员工id
     * @return: R<Employee> <br>
     * date: 2023/5/1 0001 <br>
     */
    R<Employee> getEmployeeById(Long id);
}
