package com.renzo.ordersystem.dao;

import com.renzo.ordersystem.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MiaoshaUserDao {
    @Select("SELECT * FROM miaosha_user WHERE id = #{id}")
    public MiaoshaUser getById(@Param("id") long id);
}
