package com.java.book.dao;

import com.java.book.domain.Writer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 作家Dao
 */
@Repository
public interface WriterMapper {
    /**
     *增加
     */
    public int insert(Writer writer);

    /**
     *修改
     */
    public int update(Writer writer);

    /**
     * 删除
     */
    public int delete(Integer id);

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
    boolean verifyPassword(String username, String password);

    /**
     * 根据账号查询
     */
    Writer getByUsername(String username);
}
