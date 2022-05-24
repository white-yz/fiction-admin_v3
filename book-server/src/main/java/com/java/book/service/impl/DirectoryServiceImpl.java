package com.java.book.service.impl;

import com.java.book.dao.DirectoryMapper;
import com.java.book.domain.Directory;
import com.java.book.service.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectoryServiceImpl implements DirectoryService {

    @Autowired
    private DirectoryMapper directoryMapper;

    @Override
    public boolean insert(Directory directory) {
        return directoryMapper.insert(directory)>0;
    }

    @Override
    public boolean update(Directory directory) {
        return directoryMapper.update(directory)>0;
    }

    @Override
    public boolean delete(Integer id) {
        return directoryMapper.delete(id)>0;
    }

    @Override
    public Directory selectByPrimaryKey(Integer id) {
        return directoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Directory> allDirectory() {
        return directoryMapper.allDirectory();
    }

    @Override
    public List<Directory> directoryOfName(String name) {
        return directoryMapper.directoryOfName(name);
    }

    @Override
    public List<Directory> directoryOfBookId(Integer bookId) {
        return directoryMapper.directoryOfBookId(bookId);
    }
}
