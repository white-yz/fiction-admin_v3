package com.java.book.domain;

import java.io.Serializable;

/**
 * 评价
 */
public class Rank implements Serializable {
    /*主键*/
    private Integer id;
    /*书籍id*/
    private Integer bookId;
    /*用户id*/
    private Integer consumerId;
    /*评分*/
    private Integer score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Integer consumerId) {
        this.consumerId = consumerId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
