package com.java.book.controller;

import com.alibaba.fastjson.JSONObject;
import com.java.book.domain.Consumer;
import com.java.book.domain.Writer;
import com.java.book.service.ConsumerService;
import com.java.book.service.WriterService;
import com.java.book.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作家控制类
 */
@RestController
@RequestMapping("/writer")
public class WriterController {

    @Autowired
    private WriterService writerService;

    @Autowired
    private ConsumerService consumerService;

    /**
     * 添加作家
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addWriter(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String username = request.getParameter("username").trim();      //用户名
        String password = request.getParameter("password").trim();      //密码
        String name = request.getParameter("name").trim();      //作家名
        String sex = request.getParameter("sex").trim();        //性别
        String pic = request.getParameter("pic").trim();        //头像
        String birth = request.getParameter("birth").trim();    //生日
        String location = request.getParameter("location").trim();//地区
        String introduction = request.getParameter("introduction").trim();//简介

        if (username == null || username.equals("")) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "用户名不能为空");
            return jsonObject;
        }

        Writer writer1 = writerService.getByUsername(username);
        Consumer consumer1 = consumerService.getByUsername(username);
        if (writer1 != null || consumer1 != null){
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "用户名已存在");
            return jsonObject;
        }

        if (password == null || password.equals("")){
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "密码不能为空");
            return jsonObject;
        }

        if (name == null || name.equals("")) {
            name = username;//用户名作为作者名
        }
        //把生日转换成Date格式
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = new Date();
        try {
            birthDate = dateFormat.parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //保存到作家的对象中
        Writer writer = new Writer();
        writer.setUsername(username);
        writer.setPassword(password);
        writer.setState(0);
        writer.setName(name);
        writer.setSex(new Byte(sex));
        writer.setPic(pic);
        writer.setBirth(birthDate);
        writer.setLocation(location);
        writer.setIntroduction(introduction);
        writer.setState(0);
        boolean flag = writerService.insert(writer);
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
     * 修改作家
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateWriter(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();          //主键
        String username = request.getParameter("username").trim();      //姓名
        String password = request.getParameter("password").trim();      //姓名
        String name = request.getParameter("name").trim();      //姓名
        String sex = request.getParameter("sex").trim();        //性别
        String birth = request.getParameter("birth").trim();    //生日
        String location = request.getParameter("location").trim();//地区
        String introduction = request.getParameter("introduction").trim();//简介

        if (username == null || username.equals("")) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "用户名不能为空");
            return jsonObject;
        }

//        Writer writer1 = writerService.getByUsername(username);
//        if (writer1 != null){
//            jsonObject.put(Consts.CODE, 0);
//            jsonObject.put(Consts.MSG, "用户名已存在");
//            return jsonObject;
//        }

        if (password == null || password.equals("")){
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "密码不能为空");
            return jsonObject;
        }

        if (name == null || name.equals("")) {
            name = username;//用户名作为作者名
        }
        //把生日转换成Date格式
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = new Date();
        try {
            birthDate = dateFormat.parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //保存到作家的对象中
        Writer writer = new Writer();
        writer.setId(Integer.parseInt(id));
        writer.setUsername(username);
        writer.setPassword(password);
        writer.setName(name);
        writer.setSex(new Byte(sex));
        writer.setBirth(birthDate);
        writer.setLocation(location);
        writer.setIntroduction(introduction);
        writer.setVip(Boolean.FALSE);
        boolean flag = writerService.update(writer);
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
     * 修改用户状态
     */
    @RequestMapping(value = "/update/state", method = RequestMethod.POST)
    public Object updateConsumerState(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();          //主键
        String state = request.getParameter("state").trim();     //状态

        if (state == null || state.equals("")) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "状态不能为空");
            return jsonObject;
        }
        //保存到前端用户的对象中
        Writer writer = new Writer();
        writer.setId(Integer.parseInt(id));
        writer.setState(Integer.parseInt(state));
        boolean flag = writerService.update(writer);
        if (flag) {   //保存成功
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "修改失败");
        return jsonObject;
    }

    /**
     * 成为VIP
     */
    @RequestMapping(value = "/update/vip", method = RequestMethod.POST)
    public Object VIP(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();          //主键
        String vip = request.getParameter("vip").trim();     //状态

        if (vip == null || vip.equals("")) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "VIP值不能为空");
            return jsonObject;
        }
        //保存到前端用户的对象中
        Writer writer = new Writer();
        writer.setId(Integer.parseInt(id));
        writer.setVip(Boolean.valueOf(vip));
        boolean flag = writerService.update(writer);
        if (flag) {   //保存成功
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "支付成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "支付失败");
        return jsonObject;
    }


    /**
     * 删除歌手
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object deleteWriter(HttpServletRequest request){
        String id = request.getParameter("id").trim();          //主键
        boolean flag = writerService.delete(Integer.parseInt(id));
        return flag;
    }

    /**
     * 根据主键查询整个对象
     */
    @RequestMapping(value = "/selectByPrimaryKey",method = RequestMethod.GET)
    public Object selectByPrimaryKey(HttpServletRequest request){
        String id = request.getParameter("id").trim();          //主键
        return writerService.selectByPrimaryKey(Integer.parseInt(id));
    }

    /**
     * 查询所有作家
     */
    @RequestMapping(value = "/allWriter",method = RequestMethod.GET)
    public Object allWriter(HttpServletRequest request){
        return writerService.allWriter();
    }

    /**
     * 根据作家名字模糊查询列表
     */
    @RequestMapping(value = "/writerOfName",method = RequestMethod.GET)
    public Object writerOfName(HttpServletRequest request){
        String name = request.getParameter("name").trim();          //歌手名字
        return writerService.writerOfName("%"+name+"%");
    }

    /**
     * 根据性别查询
     */
    @RequestMapping(value = "/writerOfSex",method = RequestMethod.GET)
    public Object WriterOfSex(HttpServletRequest request){
        String sex = request.getParameter("sex").trim();          //性别
        return writerService.writerOfSex(Integer.parseInt(sex));
    }

    /**
     * 更新作家图片
     */
    @RequestMapping(value = "/updateWriterPic",method = RequestMethod.POST)
    public Object updateWriterPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id")int id){
        JSONObject jsonObject = new JSONObject();
        if(avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
        }
        //文件名=当前时间到毫秒+原来的文件名
        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();
        //文件路径
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                +System.getProperty("file.separator")+"writerPic";
        //如果文件路径不存在，新增该路径
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
        //实际的文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath = "/img/writerPic/"+fileName;
        try {
            avatorFile.transferTo(dest);
            Writer writer = new Writer();
            writer.setId(id);
            writer.setPic(storeAvatorPath);
            boolean flag = writerService.update(writer);
            if(flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
                jsonObject.put("pic",storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败");
            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败"+e.getMessage());
        }finally {
            return jsonObject;
        }
    }
}






















