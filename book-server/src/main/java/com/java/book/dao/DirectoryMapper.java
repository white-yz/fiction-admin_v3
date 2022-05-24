package com.java.book.dao;

import com.java.book.domain.Book;
import com.java.book.domain.Directory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 书籍Dao
 */
@Repository
public interface DirectoryMapper {
    /**
     *增加
     */
    public int insert(Directory directory);

    /**
     *修改
     */
    public int update(Directory directory);

    /**
     * 删除
     */
    public int delete(Integer id);

    /**
     * 根据主键查询整个对象
     */
    public Directory selectByPrimaryKey(Integer id);

    /**
     * 查询所有目录
     */
    public List<Directory> allDirectory();

    /**
     * 根据目录名字模糊查询列表
     */
    public List<Directory> directoryOfName(String name);

    /**
     * 根据书籍id查询
     */
    public List<Directory> directoryOfBookId(Integer bookId);
}
