package com.java.book.dao;

import com.java.book.domain.Record;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 记录Dao
 */
@Repository
public interface RecordMapper {

    /**
     * 增加
     */
    int insert(Record record);

    /**
     * 查询读者记录
     *
     * @param consumerId
     * @return
     */
    Record selectRecordByConsumerId(@Param("consumerId") int consumerId, @Param("bookId") int bookId);


    /**
     * 查询作家记录
     *
     * @param writerId
     * @return
     */
    Record selectRecordByWriterId(@Param("writerId") int writerId, @Param("bookId") int bookId);

}
















