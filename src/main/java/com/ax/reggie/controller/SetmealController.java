package com.ax.reggie.controller;

import com.ax.reggie.common.R;
import com.ax.reggie.dto.SetmealDto;
import com.ax.reggie.entity.Setmeal;
import com.ax.reggie.service.SetmealService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * className: SetmealController
 * description: 套餐管理
 *
 * @author: axiang
 * date: 2023/5/2 0002 8:59
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     * description: 添加套餐
     * @param setmealDto:
     * @return: R<String> <br>
     * date: 2023/5/2 0002 <br>
     */
    @PostMapping
    public R<String> saveWithDish(@RequestBody SetmealDto setmealDto) {
        return setmealService.saveWithDish(setmealDto);
    }

    /**
     * description: 分页查餐信息
     * @param page:
     * @param pageSize:
     * @param name:
     * @return: R<Page> <br>
     * date: 2023/5/2 0002 <br>
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        return setmealService.listByPage(page, pageSize, name);
    }

    /**
     * description: 删除套餐, (批量)
     * @param ids:
     * @return: R<String> <br>
     * date: 2023/5/2 0002 <br>
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        log.info("删除套餐");
        return setmealService.deleteWithDish(ids);
    }

    /**
     * description: 移动端查询套餐下的信息
     * @param setmeal: 封装条件
     * @return: R<List < Setmeal>> <br>
     * date: 2023/5/2 0002 <br>
     */
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal) {

        return setmealService.listSetmeal(setmeal);
    }

}
