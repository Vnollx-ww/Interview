package com.example.interview.mapper;

import com.example.interview.domain.Cart_Product;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface CartMapper {
    @Insert("INSERT INTO carts(cart_id, user_id) VALUES(#{cart_id},#{user_id})")
    void insertCart(long cart_id,long user_id);
    @Insert("INSERT INTO cart_products(cart_product_id, cart_id, product_id, product_price,quantity,product_name) VALUES(#{cart_product_id},#{cart_id},#{product_id},#{product_price},1,#{product_name})")
    void insertProduct(long cart_id,long product_id,double product_price,long cart_product_id,String product_name);
    @Delete("DELETE FROM cart_products WHERE product_id = #{product_id} AND cart_id = #{cart_id}")
    void deleteProduct(long product_id,long cart_id);
    @Update("UPDATE cart_products " +
            "SET quantity = quantity+ #{quantity} " +
            "WHERE product_id = #{product_id} AND cart_id = #{cart_id}")
    void updateQuantity(int quantity,long product_id,long cart_id);
    @Select("SELECT COUNT(*) FROM cart_products WHERE cart_id=#{cart_id} AND product_id=#{product_id}")
    int judgeProduct(long cart_id,long product_id);
    @Select("SELECT * FROM cart_products WHERE cart_id=#{cart_id}")
    List<Cart_Product> getProductsByCart(long cart_id);
}
