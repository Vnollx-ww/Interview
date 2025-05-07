package com.example.interview.domain;

public class ReturnProduct {
    private String product_id;
    private String name;

    public long getLike_count() {
        return like_count;
    }

    public void setLike_count(long like_count) {
        this.like_count = like_count;
    }

    private long like_count;
    public ReturnProduct(String product_id,String name,double price,long like_count){
        this.name=name;
        this.price=price;
        this.product_id=product_id;
        this.like_count=like_count;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
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

    private double price;
}
