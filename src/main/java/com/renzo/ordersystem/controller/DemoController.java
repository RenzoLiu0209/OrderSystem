package com.renzo.ordersystem.controller;

import com.renzo.ordersystem.result.CodeMsg;
import com.renzo.ordersystem.result.Result;
import com.renzo.ordersystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    UserService userService;
    @RequestMapping("/")
    @ResponseBody
    public Result hello() {
        return Result.success(CodeMsg.SUCCESS, "Hello world!");
    }

    @RequestMapping("/getdb")
    @ResponseBody
    public Result getDB() {
        return Result.success(CodeMsg.SUCCESS, userService.getById(1));
    }

    @RequestMapping("/testTx")
    @ResponseBody
    public Result Tx() {
        return Result.success(CodeMsg.SUCCESS, userService.Tx());
    }

}
