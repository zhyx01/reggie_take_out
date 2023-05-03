package com.ax.reggie.service;

import com.ax.reggie.common.R;
import com.ax.reggie.dto.SetmealDto;
import com.ax.reggie.entity.Setmeal;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    /**
     * description: 添加套餐, 同时需要保存套餐和菜品的关联信息
     * @param setmealDto: 信息
     * @return: R<String> <br>
     * date: 2023/5/2 0002 <br>
     */
    R<String> saveWithDish(SetmealDto setmealDto);

    /**
     * description: 分页查套餐
     * @param page:
     * @param pageSize:
     * @param name:
     * @return: R<Page> <br>
     * date: 2023/5/2 0002 <br>
     */
    R<Page> listByPage(int page, int pageSize, String name);

    /**
     * description: 删除套餐, 同时删除关联的菜品信息
     * @param ids:
     * @return: R<String> <br>
     * date: 2023/5/2 0002 <br>
     */
    R<String> deleteWithDish(List<Long> ids);

    R<List<Setmeal>> listSetmeal(Setmeal setmeal);

}
