package com.renzo.ordersystem.dao;

import com.renzo.ordersystem.domain.MiaoshaOrder;
import com.renzo.ordersystem.domain.OrderInfo;
import com.renzo.ordersystem.vo.GoodsVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderDao {

    @Select("SELECT * FROM miaosha_order where user_id = #{userId} and goods_id = #{goodsId}")
    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(@Param("userId") long userId, @Param("goodsId") long goodsId);

    @Select("SELECT * FROM order_info where id = #{orderId}")
    public OrderInfo getOrderById(@Param("orderId") long orderId);

    @Insert("INSERT INTO order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date) values(" +
            "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel}, #{status}, #{createDate})")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class, before = false, statement = "SELECT last_insert_id()")
    public long insert(OrderInfo orderInfo);

    @Insert("INSERT INTO miaosha_order(user_id, goods_id, order_id) values(#{userId}, #{goodsId}, #{orderId})")
    public int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);
}
