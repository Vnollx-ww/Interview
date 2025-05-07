package com.example.interview.controller;

import com.example.interview.service.UserService;
import com.example.interview.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public Result login(@RequestParam String account, @RequestParam String password){
        return userService.login(account, password);
    }

    @PostMapping("/register")
    public Result regist(@RequestParam String account, @RequestParam String password){
        return userService.register(account,password);
    }
    @PostMapping("/get")
    public Result getUserById(HttpServletRequest request){
        String userId = (String) request.getAttribute("uid");
        if (userId != null) {
            return userService.getUserById(Long.parseLong(userId));
        } else {
            return Result.LogicError("未获取到用户ID");
        }
    }
}
