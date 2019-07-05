package com.renzo.ordersystem.rabbitmq;

import com.renzo.ordersystem.domain.MiaoshaUser;

public class MiaoshaMessage {
    MiaoshaUser miaoshaUser;
    long goodsId;

    public MiaoshaUser getMiaoshaUser() {
        return miaoshaUser;
    }

    public void setMiaoshaUser(MiaoshaUser miaoshaUser) {
        this.miaoshaUser = miaoshaUser;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}
