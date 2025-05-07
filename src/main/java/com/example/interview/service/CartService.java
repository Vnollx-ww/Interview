package com.example.interview.service;

import com.example.interview.utils.Result;

public interface CartService {
    Result addProduct(long cart_id,long product_id,double product_price,String product_name);
    Result deleteProduct(long cart_id,long product_id);
    Result updateQuantity(int quantity,long cart_id,long product_id);
    Result getProductsByCart(long cart_id);
}
