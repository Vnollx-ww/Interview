package com.example.interview.domain;

public class ReturnUser {
    private String user_id;
    private String account;
    private String cart_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public ReturnUser(String user_id, String account, String cart_id){
        this.account=account;
        this.cart_id=cart_id;
        this.user_id=user_id;
    }
}
