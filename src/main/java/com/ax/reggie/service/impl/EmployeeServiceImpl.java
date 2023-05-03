package com.ax.reggie.service.impl;

import com.ax.reggie.common.R;
import com.ax.reggie.entity.Employee;
import com.ax.reggie.mapper.EmployeeMapper;
import com.ax.reggie.service.EmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * className: EmployeeServiceImpl
 * description:
 *
 * @author: axiang
 * date: 2023/5/1 0001 10:38
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Override
    public R<Employee> login(HttpServletRequest request, Employee employee) {

        // 1. 将页面提交的密码进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 2. 根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = getOne(queryWrapper);

        // 3. 如果没有查询到数据则返回错误信息
        if (emp == null) {
            return R.error("账号或密码错误!");
        }

        // 4. 密码比对, 如果不一致时则返回错误信息
        if (!password.equals(emp.getPassword())) {
            return R.error("账号或密码错误!");
        }

        // 5. 查看员工状态, 如果为禁用, 则返回员工已禁用结果
        if (emp.getStatus() == 0) {
            return R.error("用户已被禁用!");
        }
        // 6. 登录成功, 经员工id存入session并返回登录成功结果
        request.getSession().setAttribute("employee", emp.getId());

        return R.success(emp);
    }

    @Override
    public R<String> addEmployeeInfo(HttpServletRequest request, Employee employee) {

        // 设置初始密码为123456, 对密码进行md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        Long empId = (Long) request.getSession().getAttribute("employee");

        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);

        save(employee);

        return R.success("添加成功");
    }

    @Override
    public R<Page> getUserByPage(int page, int pageSize, String name) {

        // 1. 构造分页构造器
        Page<Employee> pageInfo = new Page<>(page, pageSize);

        // 2. 构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(name), Employee::getName, name);

        // 3. 添加排序条件
        queryWrapper.orderByAsc(Employee::getCreateTime);

        // 4. 执行查询
        page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    @Override
    public R<String> updateEmployeeInfo(HttpServletRequest request, Employee employee) {

        // 获取登录用户id
        Long empId = (Long) request.getSession().getAttribute("employee");

        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(empId);

        // 执行更新操作
        updateById(employee);

        return R.success("员工信息修改成功");
    }

    @Override
    public R<Employee> getEmployeeById(Long id) {

        Employee employee = getById(id);

        if (employee == null) {
            return R.error("没有查询到员工信息...");
        }

        return R.success(employee);
    }
}
