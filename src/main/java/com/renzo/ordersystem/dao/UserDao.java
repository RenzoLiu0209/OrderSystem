package com.renzo.ordersystem.dao;

import com.renzo.ordersystem.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {
    @Select("SELECT * FROM user WHERE id = #{id}")
    public User getById(@Param("id") int id);

    @Insert("INSERT INTO user(id, name) VALUES(#{id}, #{name})")
    public int insert(User user);
}
