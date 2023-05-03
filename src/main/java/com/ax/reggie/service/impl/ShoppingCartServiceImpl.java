package com.ax.reggie.service.impl;

import com.ax.reggie.common.BaseContext;
import com.ax.reggie.common.R;
import com.ax.reggie.entity.ShoppingCart;
import com.ax.reggie.mapper.ShoppingCartMapper;
import com.ax.reggie.service.ShoppingCartService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * className: ShoppingCartServiceImpl
 * description:
 *
 * @author: axiang
 * date: 2023/5/2 0002 18:42
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
    @Override
    public R<ShoppingCart> add(ShoppingCart shoppingCart) {

        // 设置用户id, 指定当前是哪个用户的购物车数据
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);

        Long dishId = shoppingCart.getDishId();

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentId);

        // 判断是菜品还是套餐
        if (dishId != null) {
            // 是菜品
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
        }else {
            // 添加的是套餐
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        // 查询当前菜品或者套餐是否在购物车中
        ShoppingCart cart = getOne(queryWrapper);

        if (cart != null) {
            // 如果已存在, 就在原来的数量基础上加一
            Integer number = cart.getNumber();
            cart.setNumber(number += 1);
            updateById(cart);
        }else {
            // 如果不存在, 则添加到购物车中, 数量默认为1
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            save(shoppingCart);
            cart = shoppingCart;
        }

        return R.success(cart);
    }

    @Override
    public R<List<ShoppingCart>> listAll() {

        // 条件
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);

        List<ShoppingCart> list = list(queryWrapper);

        return R.success(list);
    }

    @Override
    public R<String> clean() {

        // 条件
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());

        remove(queryWrapper);

        return R.success("清空购物车成功");
    }

    @Override
    public R<String> sub(ShoppingCart shoppingCart) {

        // 条件, 获取当前用户的购物车
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());

        // 判断当前减一操作的是菜品还是套餐
        if (shoppingCart.getDishId() != null) {
            // 是菜品, 判断数量是否大于1
            queryWrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
            ShoppingCart cart = getOne(queryWrapper);
            Integer number = cart.getNumber();

            if (number > 1) {
                cart.setNumber(number -1);
                updateById(cart);
            } else {
                remove(queryWrapper);
            }

        } else {
            // 是套餐
            // 是菜品, 判断数量是否大于1
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
            ShoppingCart cart = getOne(queryWrapper);
            Integer number = cart.getNumber();
            // 当数量大于1的时候减一
            if (number > 1) {
                cart.setNumber(number -1);
                updateById(cart);
            } else {
                // 数量为1的时候再减一, 就从购物车中删除菜品或套餐
                remove(queryWrapper);
            }
        }
        return R.success("操作成功");
    }
}
