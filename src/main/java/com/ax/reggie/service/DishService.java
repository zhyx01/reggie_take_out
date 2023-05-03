package com.ax.reggie.service;

import com.ax.reggie.common.R;
import com.ax.reggie.dto.DishDto;
import com.ax.reggie.entity.Dish;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface DishService extends IService<Dish> {

    /**
     * description: 添加菜品信息
     * @param dishDto: 封装信息
     * @return: R<String> <br>
     * date: 2023/5/1 0001 <br>
     */
    R<String> savaDish(DishDto dishDto);

    /**
     * description: 分页查询
     * @param page:
     * @param pageSize:
     * @param name:
     * @return: R<Page> <br>
     * date: 2023/5/1 0001 <br>
     */
    R<Page> getDishByPage(int page, int pageSize, String name);

    /**
     * description: 根据id查询菜品信息和口味信息, 用于修改数据回显
     * @param id: id
     * @return: R<DishDto> <br>
     * date: 2023/5/2 0002 <br>
     */
    R<DishDto> getForUpdate(Long id);

    /**
     * description: 修改菜品信息
     * @param dishDto: 封装信息
     * @return: R<String> <br>
     * date: 2023/5/1 0001 <br>
     */
    R<String> updateDish(DishDto dishDto);

    /**
     * description: 根据条件查询对应的菜品数据
     * @param dishDto: 封装条件
     * @return: R<List < Dish>> <br>
     * date: 2023/5/2 0002 <br>
     */
    R<List<DishDto>> listDish(DishDto dishDto);
}
