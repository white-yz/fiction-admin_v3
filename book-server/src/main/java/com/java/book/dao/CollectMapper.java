package com.java.book.dao;

import com.java.book.domain.Collect;
import com.java.book.domain.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 收藏Dao
 */
@Repository
public interface CollectMapper {
    /**
     *增加
     */
    public int insert(Collect collect);

    /**
     * 删除
     */
    public int delete(Integer id);

    /**
     * 根据用户id和书籍id删除
     */
    public int deleteByUserIdSongId(@Param("userId") Integer userId, @Param("bookId") Integer songId);

    /**
     * 查询所有收藏
     */
    public List<Collect> allCollect();

    /**
     * 查询某个用户的收藏列表
     */
    public List<Collect> collectOfUserId(Integer userId);

    /**
     * 查询某个用户是否已经收藏了某个书籍
     */
    public int existSongId(@Param("userId") Integer userId, @Param("bookId") Integer songId);

    /**
     * 查询某个书籍下的所有收藏
     */
    public List<Collect> collectOfBookId(Integer bookId);
}
















