package com.ax.reggie.service.impl;

import com.ax.reggie.common.R;
import com.ax.reggie.entity.Category;
import com.ax.reggie.entity.Dish;
import com.ax.reggie.entity.Setmeal;
import com.ax.reggie.exception.CustomException;
import com.ax.reggie.mapper.CategoryMapper;
import com.ax.reggie.service.CategoryService;
import com.ax.reggie.service.DishService;
import com.ax.reggie.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * className: CategoryServiceImpl
 * description:
 *
 * @author: axiang
 * date: 2023/5/1 0001 18:05
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    @Override
    public R<String> insert(Category category) {

        save(category);
        
        return R.success("添加成功");
    }

    @Override
    public R<Page> getCategoryByPage(int page, int pageSize) {

        // 分页构造器
        Page<Category> pageInfo = new Page<>(page, pageSize);

        // 条件构造器, 排序
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);

        // 执行查询
        page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    @Override
    public R<String> deleteCategory(Long id) {

        // 1. 添加查询条件, 根据分类id进行查询
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();

        // 查询当前分类是否关联了菜品, 如果已经关联, 抛出业务异常
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int countDish = dishService.count(dishLambdaQueryWrapper);
        if (countDish > 0) {
            // 已经关联, 抛出业务异常
            throw new CustomException("当前分类已经关联菜品, 不能删除!");
        }

        // 查询当前分类是否关联了套餐, 如果已经关联, 抛出业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int countSetmeal = setmealService.count(setmealLambdaQueryWrapper);
        if (countSetmeal > 0) {
            // 已经关联, 抛出业务异常
            throw new CustomException("当前分类已经关联套餐, 不能删除!");
        }

        // 执行删除
        removeById(id);
        //baseMapper.deleteById(id);

        return R.success("删除成功");
    }

    @Override
    public R<String> updateCategory(Category category) {

        updateById(category);

        return R.success("修改成功");
    }

    @Override
    public R<List<Category>> listCategory(Category category) {

        // 条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();

        // 添加条件
        queryWrapper.eq(category.getType() != null, Category::getType, category.getType());

        // 添加排序条件
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        // 执行查询
        List<Category> categoryList = list(queryWrapper);

        return R.success(categoryList);
    }
}
