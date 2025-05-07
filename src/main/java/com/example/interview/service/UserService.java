package com.example.interview.service;

import com.example.interview.utils.Result;

public interface UserService {
    Result login(String account, String password);
    Result register(String account,String password);
    Result getUserById(long user_id);
    Result getUserByAccount(String account);
}
