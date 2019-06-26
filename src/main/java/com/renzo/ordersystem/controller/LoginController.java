package com.renzo.ordersystem.controller;

import com.renzo.ordersystem.domain.User;
import com.renzo.ordersystem.redis.RedisService;
import com.renzo.ordersystem.redis.UserKey;
import com.renzo.ordersystem.result.CodeMsg;
import com.renzo.ordersystem.result.Result;
import com.renzo.ordersystem.service.UserService;
import com.renzo.ordersystem.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result doLogin(LoginVo loginVo) {
        log.info(loginVo.toString());
        return null;
    }

}
