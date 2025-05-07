package com.example.interview.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LikeMapper {
    @Insert("INSERT INTO like_records(like_record_id,product_id, user_id, product_name) VALUES(#{like_record_id},#{product_id}, #{user_id},#{product_name})")
    void insertLike(long product_id,long user_id,String product_name,long like_record_id);
    @Delete("DELETE FROM like_records WHERE product_id = #{product_id} AND user_id = #{user_id}")
    void deleteLike(long product_id,long user_id);
    @Select("SELECT COUNT(*) FROM like_records WHERE product_id = #{product_id} AND user_id = #{user_id}")
    int judgeLike(long product_id,long user_id);
}
