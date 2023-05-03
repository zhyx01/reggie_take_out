package com.ax.reggie.service;

import com.ax.reggie.common.R;
import com.ax.reggie.entity.Orders;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OrdersService extends IService<Orders> {

    /**
     * description: 下单
     * @param orders: 订单信息
     * @return: R<String> <br>
     * date: 2023/5/2 0002 <br>
     */
    R<String> submit(Orders orders);

    R<Page> userPage(int page, int pageSize);
}
