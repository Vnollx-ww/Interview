package com.example.interview.service.serviceImpl;

import com.example.interview.domain.ReturnUser;
import com.example.interview.domain.User;
import com.example.interview.mapper.CartMapper;
import com.example.interview.mapper.UserMapper;
import com.example.interview.service.UserService;
import com.example.interview.utils.Jwt;
import com.example.interview.utils.Result;
import com.example.interview.utils.SaltPassword;
import com.example.interview.utils.SnowflakeId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CartMapper cartMapper;
    @Override
    public Result login(String account, String password) {
        try {
            User user=userMapper.getUserByAccount(account);
            if(user==null){
                return Result.LogicError("该账号不存在");
            }
            password=SaltPassword.encryptPassword(password,user.getSalt());
            if(!password.equals(user.getPassword())){
                return Result.LogicError("密码不正确");
            }
            String token= Jwt.generateToken(String.valueOf(user.getUser_id()));
            return Result.Success(token,"登录成功");
        }catch (Exception e) {
            logger.error("登录失败: ", e);
            return Result.SystemError("服务器错误，请联系管理员");
        }
    }

    @Override
    public Result register(String account, String password) {
        try {
            User user=userMapper.getUserByAccount(account);
            if(user!=null){
                return Result.LogicError("该账号已存在");
            }
            byte[] salt= SaltPassword.generateSalt();
            password=SaltPassword.encryptPassword(password,salt);
            SnowflakeId snowflakeId = new SnowflakeId(0, 0);
            long user_id=snowflakeId.nextId();
            long cart_id=snowflakeId.nextId();
            userMapper.insertUser(account,password,salt,user_id,cart_id);
            cartMapper.insertCart(cart_id,user_id);
            return Result.Success("注册成功");
        }catch (Exception e) {
            logger.error("注册用户失败: ", e);
            return Result.SystemError("服务器错误，请联系管理员");
        }
    }

    @Override
    public Result getUserById(long user_id) {
        try {
            User user=userMapper.getUserById(user_id);
            if(user==null){
                return Result.LogicError("该账号不存在");
            }
            ReturnUser returnUser=new ReturnUser(String.valueOf(user.getUser_id()),user.getAccount(),String.valueOf(user.getCart_id()));
            return Result.Success(returnUser,"获取用户信息成功");
        }catch (Exception e){
            logger.error("查询用户失败: ", e);
            return Result.SystemError("服务器错误，请联系管理员");
        }
    }

    @Override
    public Result getUserByAccount(String account) {
        try {
            User user=userMapper.getUserByAccount(account);
            if(user==null){
                return Result.LogicError("该账号不存在");
            }
            ReturnUser returnUser=new ReturnUser(String.valueOf(user.getUser_id()),user.getAccount(),String.valueOf(user.getCart_id()));
            return Result.Success(returnUser,"获取用户信息成功");
        }catch (Exception e){
            logger.error("查询用户失败: ", e);
            return Result.SystemError("服务器错误，请联系管理员");
        }
    }
}
