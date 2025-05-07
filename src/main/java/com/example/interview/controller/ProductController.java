package com.example.interview.controller;

import com.example.interview.service.ProductService;
import com.example.interview.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/{id}")
    public ModelAndView productDetail(@PathVariable Long id) {
        Result result = productService.getProductById(id);
        ModelAndView modelAndView = new ModelAndView();
        if (result.getData() == null) {
            modelAndView.setViewName("error/404");
        } else {
            modelAndView.addObject("product", result.getData());
            modelAndView.setViewName("product");
        }
        return modelAndView;
    }
    @PostMapping("/search")
    public Result getProductsByName(@RequestParam String name){
        return productService.getProductsByName(name);
    }
    @PostMapping("/get")
    public Result getProducts(@RequestParam String offset,@RequestParam String size){
        return productService.getProducts(Integer.parseInt(offset),Integer.parseInt(size));
    }
    @PostMapping("/add")
    public Result addProduct(@RequestParam String name,@RequestParam String price){
        return productService.addProduct(name,Double.parseDouble(price));
    }
    @PostMapping("/judgelike")
    public Result judgeLikeProduct(@RequestParam String product_id, HttpServletRequest request){
        String userId = (String) request.getAttribute("uid");
        if (userId != null) {
            return productService.judgeLikeProduct(Long.parseLong(userId),Long.parseLong(product_id));
        } else {
            return Result.LogicError("未获取到用户ID");
        }
    }
    @PostMapping("/like")
    public Result likeProduct(@RequestParam String name, @RequestParam String product_id,@RequestParam boolean option,HttpServletRequest request){
        String userId = (String) request.getAttribute("uid");
        if (userId != null) {
            return productService.likeProduct(Long.parseLong(userId),Long.parseLong(product_id),name,option);
        } else {
            return Result.LogicError("未获取到用户ID");
        }
    }
    @PostMapping("/count")
    public Result getProductsCount(){
        return productService.getProductsCount();
    }
}
