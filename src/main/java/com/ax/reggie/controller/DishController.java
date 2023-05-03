package com.ax.reggie.controller;

import com.ax.reggie.common.R;
import com.ax.reggie.dto.DishDto;
import com.ax.reggie.entity.Dish;
import com.ax.reggie.service.DishService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * className: DishController
 * description: 菜品管理
 *
 * @author: axiang
 * date: 2023/5/1 0001 22:59
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * description: 添加菜品信息
     * @param dishDto: 封装信息
     * @return: R<String> <br>
     * date: 2023/5/1 0001 <br>
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        log.info("添加菜品");
        return dishService.savaDish(dishDto);
    }

    /**
     * description: 分页查询
     * @param page:
     * @param pageSize:
     * @param name:
     * @return: R<Page> <br>
     * date: 2023/5/1 0001 <br>
     */
    @GetMapping("/page")
    public R<Page> getDishByPage(int page, int pageSize, String name) {
        log.info("分页查询菜品信息");
        return dishService.getDishByPage(page, pageSize, name);
    }

    /**
     * description: 根据id查询菜品信息和口味信息, 用于修改数据回显
     * @param id: id
     * @return: R<DishDto> <br>
     * date: 2023/5/2 0002 <br>
     */
    @GetMapping("/{id}")
    public R<DishDto> getForUpdate(@PathVariable Long id) {
        return dishService.getForUpdate(id);
    }

    /**
     * description: 修改菜品信息
     * @param dishDto: 封装信息
     * @return: R<String> <br>
     * date: 2023/5/1 0001 <br>
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        log.info("修改菜品");
        return dishService.updateDish(dishDto);
    }

    ///**
    // * description: 根据条件查询对应的菜品数据
    // * @param dish: 条件
    // * @return: R<List < Dish>> <br>
    // * date: 2023/5/2 0002 <br>
    // */
    //@GetMapping("/list")
    //public R<List<Dish>> list(Dish dish) {
    //    log.info("根据条件查询对应的菜品数据");
    //    return dishService.listDish(dish);
    //}

    /**
     * description: 根据条件查询对应的菜品数据
     * @param dishDto: 条件
     * @return: R<List < Dish>> <br>
     * date: 2023/5/2 0002 <br>
     */
    @GetMapping("/list")
    public R<List<DishDto>> list(DishDto dishDto) {
        log.info("根据条件查询对应的菜品数据");
        return dishService.listDish(dishDto);
    }
}
