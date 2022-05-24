package com.java.book.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 评价
 */
public class Record implements Serializable {
    /*主键*/
    private Integer id;
    /*书籍id*/
    private Integer directoryId;
    /*用户id*/
    private Integer consumerId;
    /*作家id*/
    private Integer writerId;
    /*时间*/
    private Date createTime;

    private String directoryName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(Integer directoryId) {
        this.directoryId = directoryId;
    }

    public Integer getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Integer consumerId) {
        this.consumerId = consumerId;
    }

    public Integer getWriterId() {
        return writerId;
    }

    public void setWriterId(Integer writerId) {
        this.writerId = writerId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }
}
