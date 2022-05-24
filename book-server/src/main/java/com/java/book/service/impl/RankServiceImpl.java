package com.java.book.service.impl;

import com.java.book.dao.RankMapper;
import com.java.book.domain.Rank;
import com.java.book.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 评价service实现
 */
@Service
public class RankServiceImpl implements RankService {

    @Autowired
    private RankMapper rankMapper;

    /**
     * 增加
     *
     * @param rank
     */
    @Override
    public boolean insert(Rank rank) {
        return rankMapper.insert(rank)>0;
    }

    /**
     * 查总分
     *
     * @param bookId
     */
    @Override
    public int selectScoreSum(Integer bookId) {
        return rankMapper.selectScoreSum(bookId);
    }

    /**
     * 查总评分人数
     *
     * @param bookId
     */
    @Override
    public int selectRankNum(Integer bookId) {
        return rankMapper.selectRankNum(bookId);
    }

    /**
     * 计算平均分
     *
     * @param bookId
     */
    @Override
    public int rankOfBookId(Integer bookId) {
        int rankNum = rankMapper.selectRankNum(bookId);//评分总人数
        if(rankNum==0){
            return 0;
        }
        return rankMapper.selectScoreSum(bookId)/rankNum;
    }
}
