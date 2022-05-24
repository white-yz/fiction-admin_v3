package com.java.book.domain;

import java.io.Serializable;
import java.util.Date;

//作家
public class Directory implements Serializable {
    /*主键*/
    private Integer id;
    /*书籍id*/
    private Integer bookId;
    /*目录*/
    private String name;
    /*正文内容*/
    private String content;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
