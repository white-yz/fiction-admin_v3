package com.java.book.controller;

import com.alibaba.fastjson.JSONObject;
import com.java.book.domain.Collect;
import com.java.book.service.CollectService;
import com.java.book.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 收藏控制类
 */
@RestController
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    private CollectService CollectService;

    /**
     * 添加收藏
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addCollect(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String userId = request.getParameter("userId");           //用户id
        String bookId = request.getParameter("bookId");           //小说id
        if(bookId==null||bookId.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"收藏小说为空");
            return jsonObject;
        }
        if(CollectService.existSongId(Integer.parseInt(userId),Integer.parseInt(bookId))){
            jsonObject.put(Consts.CODE,2);
            jsonObject.put(Consts.MSG,"已收藏");
            return jsonObject;
        }

        //保存到收藏的对象中
        Collect Collect = new Collect();
        Collect.setUserId(Integer.parseInt(userId));
        Collect.setBookId(Integer.parseInt(bookId));

        boolean flag = CollectService.insert(Collect);
        if(flag){   //保存成功
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"收藏成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"收藏失败");
        return jsonObject;
    }

    /**
     * 删除收藏
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object deleteCollect(HttpServletRequest request){
        String userId = request.getParameter("userId");           //用户id
        String bookIds = request.getParameter("bookIds");           //歌曲id
        String[] bookIdArr = bookIds.split(",");
        for(String id : bookIdArr ){
            CollectService.deleteByUserIdSongId(Integer.parseInt(userId),Integer.parseInt(id));
        }
        return 1;
    }

    /**
     * 查询所有收藏
     */
    @RequestMapping(value = "/allCollect",method = RequestMethod.GET)
    public Object allCollect(HttpServletRequest request){
        return CollectService.allCollect();
    }

    /**
     * 查询某个用户的收藏列表
     */
    @RequestMapping(value = "/collectOfUserId",method = RequestMethod.GET)
    public Object collectOfUserId(HttpServletRequest request){
        String userId = request.getParameter("userId");          //用户id
        return CollectService.collectOfUserId(Integer.parseInt(userId));
    }

    /**
     * 查询某个书籍下的所有收藏
     */
    @RequestMapping(value = "/collectOfBookId",method = RequestMethod.GET)
    public Object collectOfBookId(HttpServletRequest request){
        String bookId = request.getParameter("bookId");          //书籍id
        return CollectService.collectOfBookId(Integer.parseInt(bookId));
    }
}






















