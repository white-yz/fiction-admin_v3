package com.java.book.service.impl;

import com.java.book.dao.CollectMapper;
import com.java.book.domain.Collect;
import com.java.book.domain.Comment;
import com.java.book.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收藏service实现类
 */
@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectMapper collectMapper;


    /**
     * 增加
     *
     * @param collect
     */
    @Override
    public boolean insert(Collect collect) {
        return collectMapper.insert(collect)>0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return collectMapper.delete(id)>0;
    }

    /**
     * 根据用户id和书籍id删除
     *
     * @param userId
     * @param bookId
     */
    @Override
    public boolean deleteByUserIdSongId(Integer userId, Integer bookId) {
        return collectMapper.deleteByUserIdSongId(userId,bookId)>0;
    }

    /**
     * 查询所有收藏
     */
    @Override
    public List<Collect> allCollect() {
        return collectMapper.allCollect();
    }

    /**
     * 查询某个用户的收藏列表
     *
     * @param userId
     */
    @Override
    public List<Collect> collectOfUserId(Integer userId) {
        return collectMapper.collectOfUserId(userId);
    }

    /**
     * 查询某个用户是否已经收藏了某个书籍
     *
     * @param userId
     * @param bookId
     */
    @Override
    public boolean existSongId(Integer userId, Integer bookId) {
        return collectMapper.existSongId(userId,bookId)>0;
    }

    /**
     * 查询某个收藏夹下的所有收藏
     *
     * @param bookId
     */
    @Override
    public List<Collect> collectOfBookId(Integer bookId) {
        return collectMapper.collectOfBookId(bookId);
    }
}
