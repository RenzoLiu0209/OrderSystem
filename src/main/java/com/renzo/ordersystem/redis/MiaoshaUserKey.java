package com.renzo.ordersystem.redis;

public class MiaoshaUserKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;
    private MiaoshaUserKey(int expireSeconds, String prefix) {
        super(TOKEN_EXPIRE, prefix);
    }
    public static MiaoshaUserKey getByToken = new MiaoshaUserKey(TOKEN_EXPIRE, "token");
    public static MiaoshaUserKey getByName = new MiaoshaUserKey(TOKEN_EXPIRE, "name");
    public static MiaoshaUserKey getById = new MiaoshaUserKey(0, "id");
}
