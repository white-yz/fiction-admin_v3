package com.java.book.controller;

import com.alibaba.fastjson.JSONObject;
import com.java.book.domain.Book;
import com.java.book.service.BookService;
import com.java.book.utils.Consts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * 书籍控制类
 */
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * 添加书籍
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addBook(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String writerId = request.getParameter("writerId").trim();  //所属作家id
        String name = request.getParameter("name").trim();  //书名
        String introduction = request.getParameter("introduction").trim();          //简介
        String pic = request.getParameter("pic").trim();            //默认图片
        String style = request.getParameter("style").trim();    //风格
        String isCharge = request.getParameter("isCharge").trim();    //是否收费

        if(name==null || name.equals("")){
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "书名不能为空");
        }
        if(introduction==null || introduction.equals("")){
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "简介不能为空");
        }
        if(style==null || style.equals("")){
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "风格不能为空");
        }

        Book book = new Book();
        book.setWriterId(Integer.parseInt(writerId));
        book.setName(name);
        book.setIntroduction(introduction);
        book.setPic(pic);
        book.setStyle(style);
        if(StringUtils.isNotEmpty(isCharge)){
            book.setIsCharge(Integer.parseInt(isCharge));
        }else {
            book.setIsCharge(0);
        }
        boolean flag = bookService.insert(book);
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
     * 根据作家id查询书籍
     */
    @RequestMapping(value = "/writer/detail",method = RequestMethod.GET)
    public Object bookOfWriterId(HttpServletRequest request){
        String writerId = request.getParameter("writerId");
        return bookService.BookOfWriterId(Integer.parseInt(writerId));
    }

    /**
     * 修改书籍
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateBook(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();          //主键
        String name = request.getParameter("name").trim();      //书名
        String introduction = request.getParameter("introduction").trim();//简介
        String style = request.getParameter("style").trim();    //风格
        String isCharge = request.getParameter("isCharge").trim();    //是否收费

        if(name==null || name.equals("")){
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "书名不能为空");
        }
        if(introduction==null || introduction.equals("")){
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "简介不能为空");
        }
        if(style==null || style.equals("")){
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "风格不能为空");
        }

        //保存到作家的对象中
        Book book = new Book();
        book.setId(Integer.parseInt(id));
        book.setName(name);
        book.setStyle(style);
        book.setIntroduction(introduction);
        if(StringUtils.isNotEmpty(isCharge)){
            book.setIsCharge(Integer.parseInt(isCharge));
        }
        boolean flag = bookService.update(book);
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
     * 删除书籍
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object deleteBook(HttpServletRequest request){
        String id = request.getParameter("id").trim();          //主键
        boolean flag = bookService.delete(Integer.parseInt(id));
        return flag;
    }

    /**
     * 批量删除书籍
     */
    @RequestMapping(value = "/delete/batch",method = RequestMethod.GET)
    public Object deleteBatch(HttpServletRequest request){
        String ids = request.getParameter("ids");           //歌曲id
        String[] bookIdArr = ids.split(",");
        for(String id : bookIdArr ){
            bookService.delete(Integer.parseInt(id));
        }
        return 1;
    }

    /**
     * 查询所有书籍
     */
    @RequestMapping(value = "/allBook",method = RequestMethod.GET)
    public Object allBook(HttpServletRequest request){
        return bookService.allBook();
    }

    /**
     * 查询所有VIP书籍
     */
    @RequestMapping(value = "/isChargeBook",method = RequestMethod.GET)
    public Object vipBook(HttpServletRequest request){
        String isCharge = request.getParameter("isCharge").trim();
        if(StringUtils.isNotEmpty(isCharge)){
            return bookService.selectBookByCharge(Integer.parseInt(isCharge));
        } else {
            return bookService.allBook();
        }
    }

    /**
     * 根据书籍名字模糊查询列表
     */
    @RequestMapping(value = "/bookOfName",method = RequestMethod.GET)
    public Object bookOfName(HttpServletRequest request){
        String name = request.getParameter("name").trim();
        return bookService.BookOfName(name);
    }

    /**
     * 更新书籍图片
     */
    @RequestMapping(value = "/updateBookPic",method = RequestMethod.POST)
    public Object updateWriterPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id")int id){
        JSONObject jsonObject = new JSONObject();
        if(avatorFile.isEmpty()){//判断文件是否存在
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
        }
        //文件名=当前时间到毫秒(+原来的文件名)
//        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();
        String fileName = System.currentTimeMillis()+"";
        //文件路径 user.dir获取工程根目录
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                +System.getProperty("file.separator")+"bookPic";
        //如果文件路径不存在，新增该路径
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
        //实际的文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath = "/img/bookPic/"+fileName;
        try {
            avatorFile.transferTo(dest);
            Book book = new Book();
            book.setId(id);
            book.setPic(storeAvatorPath);
            boolean flag = bookService.update(book);
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

    /**
     * 根据风格模糊查询书籍列表
     */
    @RequestMapping(value = "/likeStyle",method = RequestMethod.GET)
    public Object likeStyle(HttpServletRequest request){
        String style = request.getParameter("style").trim();          //小说风格
        String isCharge = request.getParameter("isCharge").trim();          //是否收费
        return bookService.likeStyle("%"+style+"%", Integer.parseInt(isCharge));
    }

    /**
     * 根据书籍id查询书籍对象
     */
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public Object detail(HttpServletRequest request){
        String songId = request.getParameter("bookId");
        return bookService.selectByPrimaryKey(Integer.parseInt(songId));
    }
}
