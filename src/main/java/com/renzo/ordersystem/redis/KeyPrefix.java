package com.renzo.ordersystem.redis;

public interface KeyPrefix {
    public int getExpireseconds();
    public String getPrefix();
}
