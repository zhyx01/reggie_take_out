package com.ax.reggie.service.impl;

import com.ax.reggie.entity.OrderDetail;
import com.ax.reggie.mapper.OrderDetailMapper;
import com.ax.reggie.service.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
