package com.renzo.ordersystem.controller;

import com.renzo.ordersystem.rabbitmq.MQSender;
import com.renzo.ordersystem.result.CodeMsg;
import com.renzo.ordersystem.result.Result;
import com.renzo.ordersystem.service.MiaoshaUserService;
import com.renzo.ordersystem.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    MQSender sender;


//    @RequestMapping("/mq")
//    @ResponseBody
//    public Result demoMq() {
//        sender.send("I love you");
//        return Result.success(CodeMsg.LOGIN_SUCCESS, true);
//    }
//
//    @RequestMapping("/mq/topic")
//    @ResponseBody
//    public Result topic() {
//		sender.sendTopic("hello,imooc");
//        return Result.success(CodeMsg.LOGIN_SUCCESS, true);
//    }
//
//    @RequestMapping("/mq/fanout")
//    @ResponseBody
//    public Result fanout() {
//		sender.sendFanout("hello,imooc");
//        return Result.success(CodeMsg.LOGIN_SUCCESS,"Hello，world");
//    }
//
//    @RequestMapping("/mq/header")
//    @ResponseBody
//    public Result header() {
//		sender.sendHeader("hello,imooc");
//        return Result.success(CodeMsg.LOGIN_SUCCESS, "Hello，world");
//    }

}
