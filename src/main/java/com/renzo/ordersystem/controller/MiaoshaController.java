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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @RequestMapping(value = "/do_miaosha", method = RequestMethod.POST)
    @ResponseBody
    public Result doMiaosha(MiaoshaUser user, @RequestParam("goodsId") long goodsId) {
        if (user == null) {
            return Result.error(CodeMsg.USER_NOT_EXIST);
        }
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {
            return Result.error(CodeMsg.SOLID_OUT);
        }
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.BEEN_BOUGHT);
        }
        OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
        return Result.success(CodeMsg.SUCCESS, orderInfo);
    }
}
