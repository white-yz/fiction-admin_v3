package com.java.book.controller;

import com.alipay.api.AlipayApiException;
import com.java.book.domain.AliPayBean;
import com.java.book.domain.Alipay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/ali")
public class OrderController {


    @Autowired
    private Alipay aliPay;

    @RequestMapping("/pay")
    @ResponseBody
    public String alipay() throws AlipayApiException {
        AliPayBean alipayBean = new AliPayBean();
        alipayBean.setOut_trade_no(String.valueOf(System.currentTimeMillis()));
        alipayBean.setSubject("购买VIP");
        alipayBean.setTotal_amount("98");
        return aliPay.pay(alipayBean);
    }

    @RequestMapping("/success")
    @ResponseBody
    public String success(){
        return "处理成功";
    }

}
