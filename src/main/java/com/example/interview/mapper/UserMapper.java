package com.example.interview.mapper;

import com.example.interview.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO users(account, password,salt,user_id,cart_id) VALUES(#{account}, #{password},#{salt},#{user_id},#{cart_id})")
    void insertUser(String account,String password,byte[] salt,long user_id,long cart_id);
    @Select("SELECT * FROM users WHERE user_id = #{user_id}")
    User getUserById(long user_id);
    @Select("SELECT * FROM users WHERE account = #{account}")
    User getUserByAccount(String account);
}
