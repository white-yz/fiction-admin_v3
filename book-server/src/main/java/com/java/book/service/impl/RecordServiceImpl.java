package com.java.book.service.impl;

import com.java.book.dao.RecordMapper;
import com.java.book.domain.Record;
import com.java.book.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 记录service实现类
 */
@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordMapper recordMapper;

    @Override
    public Boolean insert(Record record) {
        return recordMapper.insert(record) > 0;
    }

}
