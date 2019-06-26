package com.renzo.ordersystem.controller;

import com.renzo.ordersystem.domain.User;
import com.renzo.ordersystem.redis.RedisService;
import com.renzo.ordersystem.redis.UserKey;
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
    @Autowired
    RedisService redisService;
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

    @RequestMapping("/redisGet")
    @ResponseBody
    public Result redisGet() {
        return Result.success(CodeMsg.SUCCESS, redisService.get(UserKey.getById, "1", User.class));
    }

    @RequestMapping("/redisSet")
    @ResponseBody
    public Result redisSet() {
        User u1 = new User();
        u1.setId(4);
        u1.setName("zy");
        return Result.success(CodeMsg.SUCCESS, redisService.set(UserKey.getById, "1", u1));
    }

}
