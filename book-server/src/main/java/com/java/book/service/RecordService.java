package com.java.book.service;

import com.java.book.domain.Record;

/**
 * 阅读记录service接口
 */
public interface RecordService {


    /**
     * 增加
     */
    Boolean insert(Record record);
}

