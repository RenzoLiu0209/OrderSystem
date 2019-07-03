package com.renzo.ordersystem.redis;

public class OrderKey extends BasePrefix {
    public OrderKey(String prefix) {
        super(prefix);
    }
    public static OrderKey getByUserIdGoodsId = new OrderKey("moug");
}
