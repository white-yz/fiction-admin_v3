package com.java.book.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 收藏
 */
public class Collect implements Serializable {
    private Integer id;     //主键
    private Integer userId; //用户id
    private Integer bookId; //书籍id
    private Date createTime;    //收藏时间

    private String record;    //阅读记录

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

    public Integer getBookId() {
        return bookId;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
