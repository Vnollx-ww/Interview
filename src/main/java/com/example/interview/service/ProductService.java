package com.example.interview.service;

import com.example.interview.domain.Product;
import com.example.interview.utils.Result;

import java.util.List;

public interface ProductService {
    Result getProductById(long product_id);
    Result getProductsByName(String name);

    Result getProducts(int offset,int size);
    Result addProduct(String name,double price);
    Result likeProduct(long user_id,long product_id,String name,boolean option);
    Result judgeLikeProduct(long user_id,long product_id);
    Result getProductsCount();
}
