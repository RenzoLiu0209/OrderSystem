package com.renzo.ordersystem.redis;

public class GoodsKey extends BasePrefix {
    public static final int TOKEN_EXPIRE = 60;
    public GoodsKey(int expireseconds, String prefix) {
        super(expireseconds, prefix);
    }
    public static GoodsKey getGoodsList = new GoodsKey(GoodsKey.TOKEN_EXPIRE, "gl");
    public static GoodsKey getGoodsDetail = new GoodsKey(GoodsKey.TOKEN_EXPIRE, "gd");
    public static GoodsKey getMiaoshaGoodsStock = new GoodsKey(0, "gs");
}
