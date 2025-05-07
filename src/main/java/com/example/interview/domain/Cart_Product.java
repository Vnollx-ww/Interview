package com.example.interview.domain;

import javax.persistence.*;

@Table(name = "cart_products")
@Entity
public class Cart_Product {
    public long getCart_product_id() {
        return cart_product_id;
    }

    public void setCart_product_id(long cart_product_id) {
        this.cart_product_id = cart_product_id;
    }
    @Id
    private long cart_product_id;
   private long cart_id;
   private long product_id;
   private long product_price;
   private String product_name;

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    private long quantity;

    public long getCart_id() {
        return cart_id;
    }

    public void setCart_id(long cart_id) {
        this.cart_id = cart_id;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public long getProduct_price() {
        return product_price;
    }

    public void setProduct_price(long product_price) {
        this.product_price = product_price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}