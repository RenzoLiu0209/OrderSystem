package com.renzo.ordersystem.controller;

import com.renzo.ordersystem.domain.User;
import com.renzo.ordersystem.redis.RedisService;
import com.renzo.ordersystem.redis.UserKey;
import com.renzo.ordersystem.result.CodeMsg;
import com.renzo.ordersystem.result.Result;
import com.renzo.ordersystem.service.MiaoshaUserService;
import com.renzo.ordersystem.service.UserService;
import com.renzo.ordersystem.util.ValidatorUtil;
import com.renzo.ordersystem.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    MiaoshaUserService userService;

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
        log.info(loginVo.toString());
//        String mobile = loginVo.getMobile();
//        String password = loginVo.getPassword();
//        if (StringUtils.isBlank(password)) {
//            return Result.error(CodeMsg.PASSWORD_EMPTY);
//        }
//        if (StringUtils.isBlank(mobile)) {
//            return Result.error(CodeMsg.MOBILE_EMPTY);
//        }
//        if (!ValidatorUtil.isMobile(mobile)) {
//            return Result.error(CodeMsg.MOBILE_INVALID);
//        }

        userService.doLogin(response, loginVo);
        return Result.success(CodeMsg.LOGIN_SUCCESS, true);
    }

}
