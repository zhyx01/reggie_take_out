package com.ax.reggie.controller;

import com.ax.reggie.common.R;
import com.ax.reggie.entity.OrderDetail;
import com.ax.reggie.entity.Orders;
import com.ax.reggie.service.OrdersService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * className: OrdersController
 * description:
 *
 * @author: axiang
 * date: 2023/5/2 0002 19:44
 */
@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    /**
     * description: 下单
     * @param orders: 订单信息
     * @return: R<String> <br>
     * date: 2023/5/2 0002 <br>
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {
        return ordersService.submit(orders);
    }

    @GetMapping("/userPage")
    public R<Page> userPage(int page, int pageSize) {
        return ordersService.userPage(page, pageSize);
    }
}
