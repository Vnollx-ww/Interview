package com.example.interview.service.serviceImpl;

import com.example.interview.domain.Cart_Product;
import com.example.interview.domain.Product;
import com.example.interview.domain.ReturnCartProduct;
import com.example.interview.domain.ReturnProduct;
import com.example.interview.mapper.CartMapper;
import com.example.interview.service.CartService;
import com.example.interview.service.UserService;
import com.example.interview.utils.Result;
import com.example.interview.utils.SnowflakeId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private CartMapper cartMapper;
    @Override
    public Result addProduct(long cart_id, long product_id, double product_price,String product_name) {
        try {
            int ok= cartMapper.judgeProduct(cart_id,product_id);
            if(ok>0){
                return Result.LogicError("商品已在购物车中，无需再次添加");
            }
            SnowflakeId snowflakeId = new SnowflakeId(0, 0);
            cartMapper.insertProduct(cart_id,product_id,product_price, snowflakeId.nextId(),product_name);
            return Result.Success("添加商品至购物车成功");
        }catch (Exception e) {
            logger.error("添加商品至购物车失败: ", e);
            return Result.SystemError("服务器错误，请联系管理员");
        }
    }

    @Override
    public Result deleteProduct(long cart_id, long product_id) {
        try {
            cartMapper.deleteProduct(product_id,cart_id);
            return Result.Success("从购物车删除商品成功");
        }catch (Exception e) {
            logger.error("从购物车删除商品失败: ", e);
            return Result.SystemError("服务器错误，请联系管理员");
        }
    }

    @Override
    public Result updateQuantity(int quantity,long cart_id,long product_id) {
        try {
            cartMapper.updateQuantity(quantity,product_id,cart_id);
            return Result.Success("更新购物车信息成功");
        }catch (Exception e) {
            logger.error("更新购物车信息失败: ", e);
            return Result.SystemError("服务器错误，请联系管理员");
        }
    }

    @Override
    public Result getProductsByCart(long cart_id) {
        try {
            List<Cart_Product> cartProducts=cartMapper.getProductsByCart(cart_id);
            List<ReturnCartProduct> returnCartProducts=new ArrayList<>();
            for (Cart_Product cartProduct : cartProducts) {
                ReturnCartProduct returnCartProduct=new ReturnCartProduct(String.valueOf(cartProduct.getProduct_id()),cartProduct.getProduct_name(),cartProduct.getProduct_price(),cartProduct.getQuantity());
                returnCartProducts.add(returnCartProduct);
            }
            return Result.Success(returnCartProducts,"获取购物车商品成功");
        }catch (Exception e) {
            logger.error("获取购物车商品失败: ", e);
            return Result.SystemError("服务器错误，请联系管理员");
        }
    }
}
