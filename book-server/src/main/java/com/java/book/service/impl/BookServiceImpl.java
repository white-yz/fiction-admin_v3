package com.java.book.service.impl;

import com.java.book.dao.BookMapper;
import com.java.book.domain.Book;
import com.java.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Override
    public boolean insert(Book book) {
        return bookMapper.insert(book)>0;
    }

    @Override
    public boolean update(Book book) {
        return bookMapper.update(book)>0;
    }

    @Override
    public boolean delete(Integer id) {
        return bookMapper.delete(id)>0;
    }

    @Override
    public Book selectByPrimaryKey(Integer id) {
        return bookMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Book> allBook() {
        return bookMapper.allBook();
    }

    @Override
    public List<Book> selectBookByCharge(Integer isCharge) {
        return bookMapper.selectBookByCharge(isCharge);
    }

    @Override
    public List<Book> BookOfName(String name) {
        return bookMapper.BookOfName("%"+name+"%");
    }

    @Override
    public List<Book> BookOfWriterId(Integer writerId) {
        return bookMapper.BookOfWriterId(writerId);
    }

    @Override
    public List<Book> likeStyle(String style, Integer isCharge) {
        return bookMapper.likeStyle(style, isCharge);
    }
}
