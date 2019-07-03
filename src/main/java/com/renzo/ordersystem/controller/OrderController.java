package com.renzo.ordersystem.controller;

import com.renzo.ordersystem.domain.MiaoshaOrder;
import com.renzo.ordersystem.domain.MiaoshaUser;
import com.renzo.ordersystem.domain.OrderInfo;
import com.renzo.ordersystem.result.CodeMsg;
import com.renzo.ordersystem.result.Result;
import com.renzo.ordersystem.service.GoodsService;
import com.renzo.ordersystem.service.MiaoshaService;
import com.renzo.ordersystem.service.OrderService;
import com.renzo.ordersystem.vo.GoodsVo;
import com.renzo.ordersystem.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @RequestMapping(value = "/detail")
    @ResponseBody
    public Result getOrderDetail(MiaoshaUser user, @RequestParam("orderId") long orderId) {
       if (user == null) {
           return Result.error(CodeMsg.USER_NOT_EXIST);
       }
       OrderInfo orderInfo = orderService.getOrderById(orderId);
       if (orderInfo == null) {
           return Result.error(CodeMsg.ORDER_NOT_EXIST);
       }
       long goodsId = orderInfo.getGoodsId();
       GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
       OrderDetailVo orderDetailVo = new OrderDetailVo();
       orderDetailVo.setGoodsVo(goodsVo);
       orderDetailVo.setOrderInfo(orderInfo);
       return Result.success(CodeMsg.SUCCESS, orderDetailVo);
    }
}
