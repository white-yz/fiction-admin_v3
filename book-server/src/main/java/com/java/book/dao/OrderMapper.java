package com.java.book.dao;

import com.java.book.domain.Book;
import com.java.book.domain.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单Dao
 */
@Repository
public interface OrderMapper {
    /**
     *增加
     */
    public int insert(Book book);


    /**
     * 根据账号id和类型查询是否付费
     */
   List<Order> selectOrderByParam(Integer userId);
}
