package com.example.interview.domain;

import javax.persistence.*;

@Table(name = "carts")
@Entity
public class Cart {
    @Id
    private long cart_id;
    private long user_id;

    public long getCart_id() {
        return cart_id;
    }

    public void setCart_id(long cart_id) {
        this.cart_id = cart_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
}