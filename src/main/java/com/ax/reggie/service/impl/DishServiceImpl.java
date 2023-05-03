package com.ax.reggie.service.impl;

import com.ax.reggie.common.R;
import com.ax.reggie.dto.DishDto;
import com.ax.reggie.entity.Category;
import com.ax.reggie.entity.Dish;
import com.ax.reggie.entity.DishFlavor;
import com.ax.reggie.mapper.DishMapper;
import com.ax.reggie.service.CategoryService;
import com.ax.reggie.service.DishFlavorService;
import com.ax.reggie.service.DishService;
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
 * className: DishServiceImpl
 * description:
 *
 * @author: axiang
 * date: 2023/5/1 0001 19:44
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryService categoryService;

    @Override
    @Transactional
    public R<String> savaDish(DishDto dishDto) {

        // 保存菜品的基本信息到菜品表dish中
        save(dishDto);

        // 获取当前菜品的id
        Long dishId = dishDto.getId();

        // 保存菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();

        // 通过id关联起来
        flavors = flavors.stream()
                .map((item) -> {
                    item.setDishId(dishId);
                    return item;
                }).collect(Collectors.toList());

        // 执行保存, 将菜品口味保存带菜品口味表dish_flavor中
        dishFlavorService.saveBatch(flavors);

        return R.success("菜品保存成功");
    }

    @Override
    public R<Page> getDishByPage(int page, int pageSize, String name) {

        // 分页构造器
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        // 条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();

        // 添加过滤条件
        queryWrapper.eq(name != null, Dish::getName, name);

        // 添加排序条件
        queryWrapper.orderByDesc(Dish::getCreateTime);

        // 执行分页查询
        page(pageInfo, queryWrapper);

        // 对象拷贝
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");

        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream()
                .map((item) -> {
                    DishDto dishDto = new DishDto();

                    BeanUtils.copyProperties(item, dishDto);

                    // 获取分类id
                    Long categoryId = item.getCategoryId();

                    // 根据分类id查询分类对象
                    Category category = categoryService.getById(categoryId);

                    if (category != null) {
                        String categoryName = category.getName();
                        dishDto.setCategoryName(categoryName);
                    }

                    return dishDto;
                }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }

    @Override
    public R<DishDto> getForUpdate(Long id) {

        // 查询菜品基本信息, dish表查询
        Dish dish = getById(id);

        DishDto dishDto = new DishDto();

        // 对象拷贝
        BeanUtils.copyProperties(dish, dishDto);

        // 查询当前菜品的口味信息, 从dish_flavor表中查
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());

        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);

        return R.success(dishDto);
    }

    @Override
    @Transactional
    public R<String> updateDish(DishDto dishDto) {

        // 更新dish表中的基本信息
        updateById(dishDto);

        // 清理当前菜品对应的口味数据, ===> dish_flavor表的delete操作
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());

        dishFlavorService.remove(queryWrapper);

        // 添加当前菜品对应的口味数据, ===> dish_flavor表的insert操作
        List<DishFlavor> flavors = dishDto.getFlavors();

        // 通过id关联起来
        flavors = flavors.stream()
                .map((item) -> {
                    item.setDishId(dishDto.getId());
                    return item;
                }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);

        return R.success("修改成功");
    }

    @Override
    public R<List<DishDto>> listDish(DishDto dishDto) {

        // 条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dishDto.getCategoryId() != null, Dish::getCategoryId, dishDto.getCategoryId());
        // 条件
        queryWrapper.eq(Dish::getStatus, 1);

        // 排序
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        // 执行查询
        List<Dish> list = list(queryWrapper);

        List<DishDto> dishDtoList = list.stream().map((item) -> {
            DishDto dto = new DishDto();
            BeanUtils.copyProperties(item, dto);

            Long categoryId = item.getCategoryId();

            Category category = categoryService.getById(categoryId);

            if (category != null) {
                String categoryName = category.getName();
                dto.setCategoryName(categoryName);
            }

            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId, dishId);
            List<DishFlavor> dishFlavors = dishFlavorService.list(lambdaQueryWrapper);
            dto.setFlavors(dishFlavors);
            return dto;
        }).collect(Collectors.toList());

        return R.success(dishDtoList);
    }
}
