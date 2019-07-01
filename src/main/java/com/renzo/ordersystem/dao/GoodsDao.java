package com.renzo.ordersystem.dao;

import com.renzo.ordersystem.domain.Goods;
import com.renzo.ordersystem.domain.MiaoshaGoods;
import com.renzo.ordersystem.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GoodsDao {

    @Select("SELECT g.*, mg.stock_count, mg.start_date, mg.end_date, mg.miaosha_price FROM miaosha_goods mg LEFT JOIN goods g ON mg.goods_id = g.id")
    public List<GoodsVo> listGoodsVo();

    @Select("SELECT g.*, mg.stock_count, mg.start_date, mg.end_date, mg.miaosha_price FROM miaosha_goods mg LEFT JOIN goods g ON mg.goods_id = g.id where g.id = #{goodsId}")
    public GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);

    @Update("UPDATE miaosha_goods set stock_count = stock_count - 1 where goods_id = #{goodsId}")
    public int reduceStock(MiaoshaGoods g);
}
