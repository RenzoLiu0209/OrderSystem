package com.renzo.ordersystem.service;

import com.renzo.ordersystem.dao.GoodsDao;
import com.renzo.ordersystem.domain.Goods;
import com.renzo.ordersystem.domain.MiaoshaUser;
import com.renzo.ordersystem.domain.OrderInfo;
import com.renzo.ordersystem.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MiaoshaService {
    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        goodsService.reduceStock(goods);
        OrderInfo orderInfo = orderService.createOrder(user, goods);
        return orderInfo;
    }
}
