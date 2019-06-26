package com.renzo.ordersystem.redis;

public abstract class BasePrefix implements KeyPrefix{

    private int expireseconds;
    private String prefix;

    public BasePrefix(String prefix) {
        this.expireseconds = 0;
        this.prefix = prefix;
    }

    public BasePrefix(int expireseconds, String prefix) {
        this.expireseconds = expireseconds;
        this.prefix = prefix;
    }

    public int getExpireseconds() {
        return this.expireseconds;
    }
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + this.prefix;
    }
}
