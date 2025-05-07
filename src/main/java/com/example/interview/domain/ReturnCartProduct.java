package com.example.interview.domain;

public class ReturnCartProduct {
    private String product_id;
    private String name;
    private double price;
    public ReturnCartProduct(String product_id,String name,double price,long quantity){
        this.product_id=product_id;
        this.price=price;
        this.name=name;
        this.quantity=quantity;
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

    public long  getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    private long quantity;
}
