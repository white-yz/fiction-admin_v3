package com.java.book.controller;

import com.alibaba.fastjson.JSONObject;
import com.java.book.domain.Rank;
import com.java.book.service.RankService;
import com.java.book.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RankController {

    @Autowired
    private RankService rankService;

    /**
     * 新增评价
     */
    @RequestMapping(value = "/rank/add",method = RequestMethod.POST)
    public Object add(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String bookId = request.getParameter("bookId");
        String consumerId = request.getParameter("consumerId");
        String score = request.getParameter("score");

        Rank rank = new Rank();
        rank.setBookId(Integer.parseInt(bookId));
        rank.setConsumerId(Integer.parseInt(consumerId));
        rank.setScore(Integer.parseInt(score));
        boolean flag = rankService.insert(rank);
        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"评价成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"评价失败");
        return jsonObject;
    }

    /**
     * 计算平均分
     */
    @RequestMapping(value = "/rank",method = RequestMethod.GET)
    public Object rankOfBookId(HttpServletRequest request){
        String bookId = request.getParameter("bookId");
//        System.out.printf("平均分："+rankService.rankOfBookId(Integer.parseInt(bookId)));
        return rankService.rankOfBookId(Integer.parseInt(bookId));
    }

    /**
     * 评分总人数
     */
    @RequestMapping(value = "/rank/sum",method = RequestMethod.GET)
    public Object selectRankNum(HttpServletRequest request){
        String bookId = request.getParameter("bookId");
        return rankService.selectRankNum(Integer.parseInt(bookId));
    }
}






















