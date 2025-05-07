package com.example.interview.domain;

import javax.persistence.*;

@Table(name = "products")
@Entity
public class Product {
    @Id
    private long product_id;
    private String name;
    private double price;
    private long like_count;

    public long getProduct_id() {
        return product_id;
    }

    public long getLike_count() {
        return like_count;
    }

    public void setLike_count(long like_count) {
        this.like_count = like_count;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}