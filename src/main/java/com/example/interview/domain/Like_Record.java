package com.example.interview.domain;

import javax.persistence.*;

@Table(name = "like_records")
@Entity
public class Like_Record {
    @Id
    private long like_record_id;
    private long product_id;

    public long getLike_record_id() {
        return like_record_id;
    }

    public void setLike_record_id(long like_record_id) {
        this.like_record_id = like_record_id;
    }

    private long user_id;

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    private String product_name;
}