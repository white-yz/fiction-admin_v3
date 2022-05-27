package com.java.book.controller;

import com.alibaba.fastjson.JSONObject;
import com.java.book.domain.Record;
import com.java.book.service.RecordService;
import com.java.book.utils.Consts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    /**
     * 新增记录
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object add(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String directoryId = request.getParameter("directoryId");
        String bookId = request.getParameter("bookId");
        String consumerId = request.getParameter("consumerId");
        String writerId = request.getParameter("writerId");

        Record record = new Record();
        record.setDirectoryId(Integer.parseInt(directoryId));
        record.setBookId(Integer.parseInt(bookId));
        if(StringUtils.isNotEmpty(consumerId)){
            record.setConsumerId(Integer.parseInt(consumerId));
        }
        if(StringUtils.isNotEmpty(writerId)){
            record.setWriterId(Integer.parseInt(writerId));
        }
        record.setCreateTime(new Date());
        boolean flag = recordService.insert(record);
        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"保存成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"保存失败");
        return jsonObject;
    }

    /**
     * 查询记录
     */
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public Object selectRecord(HttpServletRequest request){
        return null;
    }

}






















