package com.java.book.service;

import com.java.book.domain.Book;

import java.util.List;

//书籍service接口
public interface BookService {
    /**
     *增加
     */
    public boolean insert(Book book);

    /**
     *修改
     */
    public boolean update(Book book);

    /**
     * 删除
     */
    public boolean delete(Integer id);

    /**
     * 根据主键查询整个对象
     */
    public Book selectByPrimaryKey(Integer id);

    /**
     * 查询所有书籍
     */
    public List<Book> allBook();

    /**
     * 查询所有收费书籍
     */
    public List<Book> selectBookByCharge(Integer isCharge);

    /**
     * 根据书籍名字模糊查询列表
     */
    public List<Book> BookOfName(String name);

    /**
     * 根据作家id查询
     */
    public List<Book> BookOfWriterId(Integer writerId);

    /**
     * 根据风格模糊查询歌单列表
     */
    public List<Book> likeStyle(String style, Integer isCharge);
}
