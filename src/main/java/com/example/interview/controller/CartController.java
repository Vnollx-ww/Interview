package com.example.interview.controller;

import com.example.interview.service.CartService;
import com.example.interview.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @PostMapping("/addproduct")
    public Result addProduct(String product_id,String cart_id,String price,String product_name){
        return cartService.addProduct(Long.parseLong(cart_id),Long.parseLong(product_id),Double.parseDouble(price),product_name);
    }
    @PostMapping("/deleteproduct")
    public Result deleteProduct(String product_id,String cart_id){
        return cartService.deleteProduct(Long.parseLong(cart_id),Long.parseLong(product_id));
    }
    @PostMapping("/updatequantity")
    public Result updateQuantity(String product_id,String cart_id,String quantity){
        return cartService.updateQuantity(Integer.parseInt(quantity),Long.parseLong(cart_id),Long.parseLong(product_id));
    }
    @PostMapping("/get")
    public Result getProductsByCart(String cart_id){
        return cartService.getProductsByCart(Long.parseLong(cart_id));
    }
}
