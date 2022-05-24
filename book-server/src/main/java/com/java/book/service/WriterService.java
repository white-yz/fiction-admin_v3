package com.java.book.service;

import com.java.book.domain.Writer;

import java.util.List;

/**
 * 作家service接口
 */
public interface WriterService {
    /**
     *增加
     */
    public boolean insert(Writer singer);

    /**
     *修改
     */
    public boolean update(Writer singer);

    /**
     * 删除
     */
    public boolean delete(Integer id);

    /**
     * 根据主键查询整个对象
     */
    public Writer selectByPrimaryKey(Integer id);

    /**
     * 查询所有作家
     */
    public List<Writer> allWriter();

    /**
     * 根据作家名字模糊查询列表
     */
    public List<Writer> writerOfName(String name);

    /**
     * 根据性别查询
     */
    public List<Writer> writerOfSex(Integer sex);

    /**
     * 查看密码是否正确
     */
    public boolean verifyPassword(String username,String password);

    /**
     * 根据账号查询
     */
    public Writer getByUsername(String username);
}
