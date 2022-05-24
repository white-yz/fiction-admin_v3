package com.java.book.dao;

import com.java.book.domain.Rank;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评价Dao
 */
@Repository
public interface RankMapper {
    /**
     *增加
     */
    public int insert(Rank rank);

    /**
     * 查总分
     */
    public int selectScoreSum(Integer bookId);

    /**
     * 查总评分人数
     */
    public int selectRankNum(Integer bookId);

}
















