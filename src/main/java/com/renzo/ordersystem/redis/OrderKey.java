package com.renzo.ordersystem.redis;

public class OrderKey extends BasePrefix {
    public OrderKey(int expireseconds, String prefix) {
        super(expireseconds, prefix);
    }
}
