package com.java.book.controller;

import com.alibaba.fastjson.JSONObject;
import com.java.book.domain.Directory;
import com.java.book.service.DirectoryService;
import com.java.book.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 目录控制类
 */
@RestController
@RequestMapping("/directory")
public class DirectoryController {

    @Autowired
    private DirectoryService directoryService;

    /**
     * 添加目录
     */

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addDirectory(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String bookId = request.getParameter("bookId").trim();  //所属书籍id
        String name = request.getParameter("name").trim();          //目录名
        String content = request.getParameter("content").trim();          //内容

        if(name==null || name.equals("")){
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "目录名不能为空");
        }

        if(content==null || content.equals("")){
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "内容不能为空");
        }

        Directory directory = new Directory();
        directory.setBookId(Integer.parseInt(bookId));
        directory.setName(name);
        directory.setContent(content);
        boolean flag = directoryService.insert(directory);
        if(flag){   //保存成功
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"添加成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"添加失败");
        return jsonObject;
    }

    /**
     * 根据书籍id查询书籍
     */
    @RequestMapping(value = "/book/detail",method = RequestMethod.GET)
    public Object bookOfWriterId(HttpServletRequest request){
        String bookId = request.getParameter("bookId");
        return directoryService.directoryOfBookId(Integer.parseInt(bookId));
    }

    /**
     * 修改目录
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateDirectory(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();
        String name = request.getParameter("name").trim();
        String content = request.getParameter("content").trim();

        if(name==null || name.equals("")){
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "内容不能为空");
        }

        if(content==null || content.equals("")){
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "内容不能为空");
        }
        //保存到作家的对象中
        Directory directory = new Directory();
        directory.setId(Integer.parseInt(id));
        directory.setName(name);
        directory.setContent(content);
        boolean flag = directoryService.update(directory);
        if(flag){   //保存成功
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"修改失败");
        return jsonObject;
    }


    /**
     * 删除目录
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object deleteDirectory(HttpServletRequest request){
        String id = request.getParameter("id").trim();          //主键
        boolean flag = directoryService.delete(Integer.parseInt(id));
        return flag;
    }

    /**
     * 查询所有目录
     */
    @RequestMapping(value = "/allDirectory",method = RequestMethod.GET)
    public Object allDirectory(HttpServletRequest request){
        return directoryService.allDirectory();
    }

    /**
     * 根据目录名字模糊查询列表
     */
    @RequestMapping(value = "/bookOfName",method = RequestMethod.GET)
    public Object bookOfName(HttpServletRequest request){
        String name = request.getParameter("name").trim();
        return directoryService.directoryOfName("%"+name+"%");
    }

    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public Object detail(HttpServletRequest request){
        String songId = request.getParameter("id");
        return directoryService.selectByPrimaryKey(Integer.parseInt(songId));
    }
}
