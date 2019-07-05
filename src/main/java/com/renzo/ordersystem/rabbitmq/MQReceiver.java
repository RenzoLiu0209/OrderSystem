package com.renzo.ordersystem.rabbitmq;

import com.renzo.ordersystem.domain.MiaoshaOrder;
import com.renzo.ordersystem.domain.MiaoshaUser;
import com.renzo.ordersystem.redis.RedisService;
import com.renzo.ordersystem.service.GoodsService;
import com.renzo.ordersystem.service.MiaoshaService;
import com.renzo.ordersystem.service.OrderService;
import com.renzo.ordersystem.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQReceiver {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    RedisService redisService;

    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);
    @RabbitListener(queues=MQConfig.MIAOSHA_QUEUE)
    public void reveive(String message) {
        log.info("Receive:" + message);
        MiaoshaMessage mm = RedisService.stringToBean(message, MiaoshaMessage.class);
        MiaoshaUser user = mm.getMiaoshaUser();
        long goodsId = mm.getGoodsId();

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {
            return;
        }


        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return;
        }

        miaoshaService.miaosha(user, goods);
    }
//    @RabbitListener(queues=MQConfig.QUEUE)
//    public void reveive(String message) {
//        log.info("Receive:" + message);
//    }
//
//    @RabbitListener(queues=MQConfig.TOPIC_QUEUE1)
//    public void receiveTopic1(String message) {
//        log.info(" topic  queue1 message:"+message);
//    }
//
//    @RabbitListener(queues=MQConfig.TOPIC_QUEUE2)
//    public void receiveTopic2(String message) {
//        log.info(" topic  queue2 message:" + message);
//    }
//
//    @RabbitListener(queues=MQConfig.HEADER_QUEUE)
//    public void receiveHeaderQueue(byte[] message) {
//        log.info(" header  queue message:"+new String(message));
//    }
}
