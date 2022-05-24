package com.java.book.domain;

import java.io.Serializable;

/**
 * 订单
 */
public class Order implements Serializable {

    private Integer id;     //主键

    private Integer userId; //用户id


    private Integer directoryId; //目录id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(Integer directoryId) {
        this.directoryId = directoryId;
    }

}
