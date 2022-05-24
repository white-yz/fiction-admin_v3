package com.java.book.service;

import com.java.book.domain.Book;
import com.java.book.domain.Directory;

import java.util.List;

//书籍service接口
public interface DirectoryService {
    /**
     *增加
     */
    public boolean insert(Directory directory);

    /**
     *修改
     */
    public boolean update(Directory directory);

    /**
     * 删除
     */
    public boolean delete(Integer id);

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
