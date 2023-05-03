package com.ax.reggie.service;

import com.ax.reggie.common.R;
import com.ax.reggie.entity.ShoppingCart;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ShoppingCartService extends IService<ShoppingCart> {

    /**
     * description: 添加购物车
     * @param shoppingCart:
     * @return: R<ShoppingCart> <br>
     * date: 2023/5/2 0002 <br>
     */
    R<ShoppingCart> add(ShoppingCart shoppingCart);

    /**
     * description: 查看购物车
     * @param :
     * @return: R<List < ShoppingCart>> <br>
     * date: 2023/5/2 0002 <br>
     */
    R<List<ShoppingCart>> listAll();

    /**
     * description: 清空购物车
     * @param :
     * @return: R<String> <br>
     * date: 2023/5/2 0002 <br>
     */
    R<String> clean();

    /**
     * description: 购物车数量减一
     * @param shoppingCart:
     * @return: R<String> <br>
     * date: 2023/5/2 0002 <br>
     */
    R<String> sub(ShoppingCart shoppingCart);
}
