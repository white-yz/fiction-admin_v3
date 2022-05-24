package com.java.book.service.impl;

import com.java.book.dao.WriterMapper;
import com.java.book.domain.Writer;
import com.java.book.service.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 歌手service实现类
 */
@Service
public class WriterServiceImpl implements WriterService {

    @Autowired
    private WriterMapper writerMapper;

    /**
     * 增加
     *
     * @param writer
     */
    @Override
    public boolean insert(Writer writer) {
        return writerMapper.insert(writer)>0;
    }

    /**
     * 修改
     *
     * @param writer
     */
    @Override
    public boolean update(Writer writer) {
        return writerMapper.update(writer)>0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return writerMapper.delete(id)>0;
    }

    /**
     * 根据主键查询整个对象
     *
     * @param id
     */
    @Override
    public Writer selectByPrimaryKey(Integer id) {
        return writerMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有歌手
     */
    @Override
    public List<Writer> allWriter() {
        return writerMapper.allWriter();
    }

    /**
     * 根据歌手名字模糊查询列表
     *
     * @param name
     */
    @Override
    public List<Writer> writerOfName(String name) {
        return writerMapper.writerOfName(name);
    }

    /**
     * 根据性别查询
     *
     * @param sex
     */
    @Override
    public List<Writer> writerOfSex(Integer sex) {
        return writerMapper.writerOfSex(sex);
    }

    /**
     * 查看密码是否正确
     */
    @Override
    public boolean verifyPassword(String username, String password) {
        return writerMapper.verifyPassword(username, password);
    }

    /**
     * 根据账号查询
     */
    @Override
    public Writer getByUsername(String username) {
        return writerMapper.getByUsername(username);
    }
}
