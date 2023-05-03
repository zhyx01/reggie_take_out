package com.ax.reggie.controller;

import com.ax.reggie.common.R;
import com.ax.reggie.entity.ShoppingCart;
import com.ax.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * className: ShoppingCartController
 * description:
 *
 * @author: axiang
 * date: 2023/5/2 0002 18:43
 */
@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * description: 添加菜品或套餐到购物车
     * @param shoppingCart:
     * @return: R<ShoppingCart> <br>
     * date: 2023/5/2 0002 <br>
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        return shoppingCartService.add(shoppingCart);
    }

    /**
     * description: 查看购物车
     * @param :
     * @return: R<List < ShoppingCart>> <br>
     * date: 2023/5/2 0002 <br>
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list() {
        return shoppingCartService.listAll();
    }

    /**
     * description: 清空购物车
     * @param :
     * @return: R<String> <br>
     * date: 2023/5/2 0002 <br>
     */
    @DeleteMapping("/clean")
    public R<String> clean() {
        return shoppingCartService.clean();
    }

    /**
     * description: 购物车数量减一
     * @param shoppingCart:
     * @return: R<String> <br>
     * date: 2023/5/2 0002 <br>
     */
    @PostMapping("/sub")
    public R<String> sub(@RequestBody ShoppingCart shoppingCart) {
        log.info(shoppingCart.toString());
        return shoppingCartService.sub(shoppingCart);
    }
}
