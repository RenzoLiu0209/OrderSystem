package com.renzo.ordersystem.controller;

import com.renzo.ordersystem.domain.MiaoshaOrder;
import com.renzo.ordersystem.domain.MiaoshaUser;
import com.renzo.ordersystem.rabbitmq.MQSender;
import com.renzo.ordersystem.rabbitmq.MiaoshaMessage;
import com.renzo.ordersystem.redis.GoodsKey;
import com.renzo.ordersystem.redis.RedisService;
import com.renzo.ordersystem.result.CodeMsg;
import com.renzo.ordersystem.result.Result;
import com.renzo.ordersystem.service.GoodsService;
import com.renzo.ordersystem.service.MiaoshaService;
import com.renzo.ordersystem.service.OrderService;
import com.renzo.ordersystem.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    RedisService redisService;

    @Autowired
    MQSender sender;

    private Map<Long, Boolean> localOverMap = new HashMap<>();

    public static int count = 0;
    private static Logger log = LoggerFactory.getLogger(MiaoshaController.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        if (goodsList == null) {
            return;
        }
        for (GoodsVo good: goodsList) {
            redisService.set(GoodsKey.getMiaoshaGoodsStock, "" + good.getId(), good.getStockCount());
            localOverMap.put(good.getId(), false);
        }
    }

    @RequestMapping(value = "/do_miaosha", method = RequestMethod.POST)
    @ResponseBody
    public Result doMiaosha(MiaoshaUser user, @RequestParam("goodsId") long goodsId) {
        if (user == null) {
            return Result.error(CodeMsg.USER_NOT_EXIST);
        }

        if (localOverMap.get(goodsId)) {
            return Result.error(CodeMsg.SOLID_OUT);
        }

        long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock, "" + goodsId);
        if (stock < 0) {
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.SOLID_OUT);
        }

        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.BEEN_BOUGHT);
        }

        MiaoshaMessage mm = new MiaoshaMessage();
        mm.setMiaoshaUser(user);
        mm.setGoodsId(goodsId);
        count++;
        log.info("" + count);
        sender.sendMiaosha(mm);
        return Result.success(CodeMsg.SUCCESS, 0);

//        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
//        int stock = goods.getStockCount();
//        if (stock <= 0) {
//            return Result.error(CodeMsg.SOLID_OUT);
//        }
//
//        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
//        if (order != null) {
//            return Result.error(CodeMsg.BEEN_BOUGHT);
//        }
//
//        OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
//        return Result.success(CodeMsg.SUCCESS, orderInfo);
    }

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    @ResponseBody
    public Result miaoshaResult(MiaoshaUser user, @RequestParam("goodsId") long goodsId) {
        if (user == null) {
            return Result.error(CodeMsg.USER_NOT_EXIST);
        }
        long result = miaoshaService.getMiaoshaResult(user.getId(), goodsId);
        return Result.success(CodeMsg.SUCCESS, result);
    }
}
