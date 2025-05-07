package com.example.interview.mapper;

import com.example.interview.domain.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ProductMapper {
    @Insert("INSERT INTO products(product_id,name,price) VALUES(#{product_id}, #{name},#{price})")
    void insertUser(long product_id,String name,double price);
    @Select("SELECT * FROM products where product_id = #{product_id}")
    Product getProductById(long product_id);
    @Select("SELECT * FROM products LIMIT #{size} OFFSET #{offset}")
    List<Product> getProducts(int offset, int size);
    @Select("SELECT COUNT(*) FROM products")
    int getProductsCount();
    @Select("SELECT * FROM products WHERE name LIKE CONCAT('%', #{name}, '%')")
    List<Product> getProductsByName(String name);
    @Update("UPDATE products SET like_count=like_count+#{num} WHERE product_id=#{product_id}")
    void addLikeCount(long product_id,int num);
    @Select("SELECT like_count FROM products WHERE product_id = #{product_id} ")
    int getLikeCount(long product_id);
    @Update("UPDATE products SET like_count=#{num} WHERE product_id=#{product_id}")
    void updateLikeCount(long product_id,long num);
}
