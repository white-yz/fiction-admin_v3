package com.java.book.dao;

import com.java.book.domain.Book;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 书籍Dao
 */
@Repository
public interface BookMapper {
    /**
     *增加
     */
    public int insert(Book book);

    /**
     *修改
     */
    public int update(Book book);

    /**
     * 删除
     */
    public int delete(Integer id);

    /**
     * 根据主键查询整个对象
     */
    public Book selectByPrimaryKey(Integer id);

    /**
     * 查询所有书籍
     */
    public List<Book> allBook();

    /**
     * 根据书籍名字模糊查询列表
     */
    public List<Book> BookOfName(@Param("name") String name);

    /**
     * 根据作家id查询
     */
    public List<Book> BookOfWriterId(Integer writerId);

    /**
     * 根据风格模糊查询歌单列表
     */
    public List<Book> likeStyle(@Param("style") String style, @Param("isCharge") Integer isCharge);

    /**
     * 查询所有收费书籍
     */
    public List<Book> selectBookByCharge(@Param("isCharge") Integer isCharge);
}
