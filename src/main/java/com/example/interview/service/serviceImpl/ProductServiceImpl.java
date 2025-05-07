package com.example.interview.service.serviceImpl;

import com.example.interview.domain.Product;
import com.example.interview.domain.ReturnProduct;
import com.example.interview.mapper.LikeMapper;
import com.example.interview.mapper.ProductMapper;
import com.example.interview.service.ProductService;
import com.example.interview.service.UserService;
import com.example.interview.utils.RedisUtils;
import com.example.interview.utils.Result;
import com.example.interview.utils.SnowflakeId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private LikeMapper likeMapper;
    @Resource
    private RedisTemplate<String, Integer> redisTemplate;
    private static final long EXPIRE_TIME = 1;
    private static final TimeUnit TIME_UNIT = TimeUnit.HOURS;
    private static final String REDIS_KEY_PREFIX = "product-id:";
    @Resource
    private RedisUtils redisUtils;
    @Override
    public Result getProductById(long product_id) {
        try {
            Product product= productMapper.getProductById(product_id);
            if(product==null){
                return Result.LogicError("商品不存在或已被删除");
            }
            long num= redisUtils.getValueAsLong("product-id:" + product_id);
            ReturnProduct returnProduct=new ReturnProduct(String.valueOf(product.getProduct_id()),product.getName(),product.getPrice(), num);
            return Result.Success(returnProduct,"获取商品信息成功");
        }catch (Exception e){
            logger.error("查询商品失败: ", e);
            return Result.SystemError("服务器错误，请联系管理员");
        }
    }

    @Override
    public Result getProductsByName(String name) {
        try {
            List<Product> products= productMapper.getProductsByName(name);
            List<ReturnProduct> returnProducts=new ArrayList<>();
            for (Product product : products) {
                ReturnProduct returnProduct=new ReturnProduct(String.valueOf(product.getProduct_id()),product.getName(),product.getPrice(),product.getLike_count());
                returnProducts.add(returnProduct);
            }
            return Result.Success(returnProducts,"获取商品列表信息成功");
        }catch (Exception e){
            logger.error("查询商品列表失败: ", e);
            return Result.SystemError("服务器错误，请联系管理员");
        }
    }

    @Override
    public Result getProducts(int offset, int size) {
        try {
            List<Product> products= productMapper.getProducts(offset,size);
            List<ReturnProduct> returnProducts=new ArrayList<>();
            for (Product product : products) {
                ReturnProduct returnProduct=new ReturnProduct(String.valueOf(product.getProduct_id()),product.getName(),product.getPrice(),product.getLike_count());
                returnProducts.add(returnProduct);
            }
            return Result.Success(returnProducts,"获取商品列表信息成功");
        }catch (Exception e){
            logger.error("查询商品列表失败: ", e);
            return Result.SystemError("服务器错误，请联系管理员");
        }
    }

    @Override
    public Result addProduct(String name, double price) {
        try {
            SnowflakeId snowflakeId = new SnowflakeId(0, 0);
            productMapper.insertUser(snowflakeId.nextId(),name,price);
            return Result.Success("添加商品成功");
        }catch (Exception e) {
            logger.error("添加商品失败: ", e);
            return Result.SystemError("服务器错误，请联系管理员");
        }
    }

    @Override
    public Result likeProduct(long user_id, long product_id,String name,boolean option) {
        String redisKey = "product-id:" + product_id;
        try {
            if (option){
                Long currentCount = redisTemplate.opsForValue().increment(redisKey, 1);
                SnowflakeId snowflakeId = new SnowflakeId(0, 0);
                likeMapper.insertLike(product_id,user_id,name,snowflakeId.nextId());
                if (currentCount == null) {
                    productMapper.addLikeCount(product_id,1);
                    redisTemplate.opsForValue().setIfAbsent(redisKey, productMapper.getLikeCount(product_id));
                    redisTemplate.expire(redisKey, EXPIRE_TIME, TIME_UNIT);
                    return Result.Success("点赞成功");
                }
                return Result.Success("点赞成功");

            }else{
                Long currentCount = redisTemplate.opsForValue().increment(redisKey, -1);
                likeMapper.deleteLike(product_id,user_id);
                if (currentCount == null) {
                    productMapper.addLikeCount(product_id,-1);
                    redisTemplate.opsForValue().setIfAbsent(redisKey, productMapper.getLikeCount(product_id));
                    redisTemplate.expire(redisKey, EXPIRE_TIME, TIME_UNIT);
                    return Result.Success("取消点赞成功");
                }
                return Result.Success("取消点赞成功");
            }
        }catch (Exception e) {
            logger.error("操作点赞失败: ", e);
            if (option) {
                if (Boolean.TRUE.equals(redisTemplate.hasKey(redisKey))) {
                    redisTemplate.opsForValue().increment(redisKey, -1);
                }
            } else {
                if (Boolean.TRUE.equals(redisTemplate.hasKey(redisKey))) {
                    redisTemplate.opsForValue().increment(redisKey, 1);
                }
            }
            return Result.SystemError("服务器错误，请联系管理员");
        }
    }


    @Override
    public Result judgeLikeProduct(long user_id, long product_id) {
        try {
            int ok=likeMapper.judgeLike(product_id,user_id);
            return Result.Success(ok,"判断是否点赞成功");
        }catch (Exception e) {
            logger.error("判断是否点赞失败: ", e);
            return Result.SystemError("服务器错误，请联系管理员");
        }
    }

    @Override
    public Result getProductsCount() {
        try {
            int number= productMapper.getProductsCount();
            return Result.Success(number,"获取商品数量成功");
        }catch (Exception e) {
            logger.error("获取商品数量失败: ", e);
            return Result.SystemError("服务器错误，请联系管理员");
        }
    }
    @Scheduled(cron = "0 */1 * * * ?") // Cron 表达式：0分每5分钟执行
    public void syncLikeCountToDatabase() {
        logger.info("开始同步 Redis 点赞计数到数据库...");
        Set<String> redisKeys = redisTemplate.keys(REDIS_KEY_PREFIX + "*");
        if (redisKeys == null || redisKeys.isEmpty()) {
            logger.info("无需要同步的 Redis 键");
            return;
        }
        for (String key : redisKeys) {
            try {
                long productId = Long.parseLong(key.replace(REDIS_KEY_PREFIX, ""));
                Integer count = redisTemplate.opsForValue().get(key);
                if (count != null) {
                    productMapper.updateLikeCount(productId, count);
                }
            } catch (NumberFormatException e) {
                logger.error("无效的 Redis 键格式: {}", key);
            }
        }

    }
}
