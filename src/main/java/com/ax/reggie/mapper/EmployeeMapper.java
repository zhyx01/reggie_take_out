package com.ax.reggie.mapper;

import com.ax.reggie.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * className: EmployeeMapper
 * description:
 *
 * @author: axiang
 * date: 2023/5/1 0001 10:37
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
