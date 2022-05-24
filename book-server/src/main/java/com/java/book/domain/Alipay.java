package com.java.book.domain;


import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Alipay {


    private final String format = "json";

    /**
     * appId
     */
    @Value("2016091300504112")
    private String appId;

    /**
     * 商户私钥
     */
    @Value("MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCdr6EsFoQON6arLvBjcEZM5mHk6IKeNH0Wrk4m74xuo+dXjHqNOoN2KYTcV8WzFY21q2fTCgWmmTUn3M4R6NrOGnrNt0L5By8/zOR3mCmElvDkJIugOlWfXvvnarm/1aUGFgCf4WMpXIFRJHErlvc5ReNFPuTzF3x+ncV7Oiqb71p6CpsAs/cLhHpx8UiwCwbugIHnVQ2BFreNT0g0SdYDQLzDaDNan8oAG1Xejy9h4QlN43P0UTmgSjcXPMNT+xpNq/vn0+3GVeFvrUYc6rSiBBl3q9lf8QYRlliMezQPcbZYY4hOdx6FL2kcmUQOh0FkbNX1o2dSNARkw3/dL+3jAgMBAAECggEAUVbdXq6mo+bVFIx79+9cdCKK8Nd3OUB+Hgr/yVncKN5nYWPXe65pTbSRElA8bOp7VPD794K983goUU23BsoExDgojKEx7N9l4hVA7OgS0pBB5o1rcNVmgXUvn+oPswLzwzXqUI6k5hsPodmYfxG6qVKBr6CH3VWKJI4h5jTU8eYtf8bO9icvs/Mjbnvqc2cUOgs6x0Yr6wwhsbbI5lp9/7oqUJvkze1vWTwhkIN6agTG8ISm3oPRSoJm7yotkA1yf8PyMK4VM7ZKwHJo+3QNlhky3NDyoZfs3NEcWE713xKNeDOxRkpGrz35M2D2bPElrahleNYKnxMTfjQ1IWFLwQKBgQDui/EpIYbf/j0M9MNSIeCgQ+F0jCnMNFn6M3BzMK85ZrfXA67U8d540M+h+xOURs9TtjGzt2swYmaJTcTVfX3vnfx91yqIK0GyPA+WNZdrT39tp5E9rTbvQ7rSFmiaYuV7zGIhT5p0xNEGAEP1yT353xoRtRKNrI4wwp55aG72swKBgQCpOSRIgLqy+om5HK3TyXpbBsTg9ixd5l7F4QXfMr2UVysWZ5uFnx/vV2d895Kavv4DZiRnsKIgVfUdgGY/wbeXyIz2LWABAzyK/cfueDonEAozD050XKMcPFO9RrgABnocYxMBS58XHm5JSv6V6Io/bVkLF6HFgrXBVAcab45EEQKBgAeqY5OIykbEar3R17z57bf4tnQCVQ2E2SdbI8/vzpGvpVS5f5LEOvvy6dkwsk5fgCYWgr5dtIoXf2gGIb8iizt5/jUlBu7dRP0vROj0y+rc4SFq8xXiTROfP4+kvLPJXcp+pOChKDt8X6MruwNY9i7jZnkPMotu8hrLBkCshHRVAoGAArDH5Oc3ELVvDyRnlyBy85ON+c2wMuYAx8CmKUBTR1KDet222U8IYPPQz2nYSOGUhFVRatyiF0WGmit6dLUNyDoGE7vw9NMqWUNjtGTw98IHh3xM0QjQBxjmc/H15vozYtQobzLWmrV/J2cr2RJTcGK6sbR0fAFyBlrhqgfwCSECgYAHSSQXqBtZPq4tceRshftWEz56pq2uK6w8S2oxb0dzLIKjm/xGSo9qDWCUl7ceOvirh1vlJfme4LGFuzng42xndCDlqxX4eCMK/oSFbCcIQ68V8d5mF1LD/KaQ3USYGjLCPtGSD2DpXoE1Be0OdVuDOhINEKXf1Cdqb3d/ENk50w==")
    private String privateKey;

    /**
     * 支付宝公钥
     */
    @Value("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsbxWe96fKf6+zdzyij4eX0SmnayN8RQNYN6qKPi5Z86kUOHytH8cOj7WALKFzGY0CEeFgaBYaeauRq8hrnv2a8fUd/fWrWM4PvZNKWTZwx4tATjGZ6crXMaBtssYKe+GQ22lFp7ZfhaEefp7GFheoIihi/n+vORdZ/ENe839sJeqdGucb1ahNwxFU0Rco8PuHgxP2BzOuhYboIbHpwv3N5X6BR6DvQVBA9M/4NQl7zHwY/AJ+EA+1N7f6kMOQBwyPHR5v+uKpYu++kxzHY6wN9+YXX0dCSLbEewUZqKWAXbyYQVbTSElEFzxdIccqY2j0T/PwNnXlOS2cR2rBX14pQIDAQAB")
    private String publicKey;

    /**
     * 服务器异步通知页面路径，需要公网能访问到
     */
    @Value("http://localhost:8081/#/")
    private String notifyUrl;

    /**
     * 服务器同步通知页面路径，填写自己的成功页面路径
     */
    @Value("http://localhost:8081/#/")
    private String returnUrl;

    /**
     * 签名方式
     */
    @Value("RSA2")
    private String signType;

    /**
     * 字符编码格式
     */
    @Value("utf-8")
    private String charset;

    /**
     * 支付宝网关
     */
    @Value("https://openapi.alipaydev.com/gateway.do")
    private String gatewayUrl;

    public String pay(AliPayBean aliPayBean) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(
                gatewayUrl, appId, privateKey, format, charset, publicKey, signType);
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(returnUrl);
        alipayRequest.setNotifyUrl(notifyUrl);
        alipayRequest.setBizContent(JSON.toJSONString(aliPayBean));
        return alipayClient.pageExecute(alipayRequest).getBody();
    }
}
