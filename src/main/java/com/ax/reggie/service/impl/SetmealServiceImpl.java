package com.ax.reggie.service.impl;

import com.ax.reggie.common.R;
import com.ax.reggie.dto.SetmealDto;
import com.ax.reggie.entity.Category;
import com.ax.reggie.entity.Setmeal;
import com.ax.reggie.entity.SetmealDish;
import com.ax.reggie.exception.CustomException;
import com.ax.reggie.mapper.SetmealMapper;
import com.ax.reggie.service.CategoryService;
import com.ax.reggie.service.SetmealDishService;
import com.ax.reggie.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * className: SetmealServiceImpl
 * description:
 *
 * @author: axiang
 * date: 2023/5/1 0001 19:45
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private CategoryService categoryService;

    @Override
    @Transactional
    public R<String> saveWithDish(SetmealDto setmealDto) {

        // 保存套餐基本信息, 操作setmeal表, insert操作
        save(setmealDto);

        // 保存套餐和菜品的关联信息, 操作setmeal_dish表, insert操作
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream()
                .map((item) -> {
                    item.setSetmealId(setmealDto.getId());
                    return item;
                }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);

        return R.success("添加套餐成功");
    }

    @Override
    public R<Page> listByPage(int page, int pageSize, String name) {

        // 构造分页构造器
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> dtoPage = new Page<>();

        // 条件
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Setmeal::getName, name);
        queryWrapper.eq(Setmeal::getStatus, 1);
        // 排序
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        // 执行分页查询
        page(pageInfo, queryWrapper);

        // 对象拷贝
        BeanUtils.copyProperties(pageInfo, dtoPage, "records");
        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream()
                .map((item) -> {
                    SetmealDto setmealDto = new SetmealDto();

                    // 对象拷贝
                    BeanUtils.copyProperties(item, setmealDto);
                    // 获取分类id
                    Long categoryId = item.getCategoryId();
                    // 根据分类id查询分类信息
                    Category category = categoryService.getById(categoryId);

                    if (category != null) {
                        // 设置分类名称
                        String categoryName = category.getName();
                        setmealDto.setCategoryName(categoryName);
                    }
                    return setmealDto;
                }).collect(Collectors.toList());

        // 设置records
        dtoPage.setRecords(list);

        return R.success(dtoPage);
    }

    @Override
    @Transactional
    public R<String> deleteWithDish(List<Long> ids) {

        // 查询套餐状态, 确定是否可以删除
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Setmeal::getStatus, 1);
        queryWrapper.in(Setmeal::getId, ids);

        int count = count(queryWrapper);
        if (count > 0) {
            // 如果不可以, 抛出业务异常
            throw new CustomException("套餐正在售卖中, 不能删除");
        }

        // 如果可以删除, 先删除套餐表中的数据
        removeByIds(ids);

        // 删除关系表中的数据
        LambdaQueryWrapper<SetmealDish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);

        setmealDishService.remove(dishLambdaQueryWrapper);

        return R.success("删除套餐信息成功");
    }

    @Override
    public R<List<Setmeal>> listSetmeal(Setmeal setmeal) {

        // 构造条件
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = list(queryWrapper);

        return R.success(list);
    }
}
