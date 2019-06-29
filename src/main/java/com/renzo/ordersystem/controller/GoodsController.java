package com.renzo.ordersystem.controller;

import com.renzo.ordersystem.domain.MiaoshaUser;
import com.renzo.ordersystem.service.MiaoshaUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @RequestMapping("/to_list")
    public String toLogin(HttpServletResponse response, Model model,
//                          @CookieValue(value = MiaoshaUserService.COOKIE_NAME_TOEKN, required = false) String cookieToken,
//                          @RequestParam(value = MiaoshaUserService.COOKIE_NAME_TOEKN, required = false) String paramToken,
                          MiaoshaUser user) {
//        if (StringUtils.isBlank(cookieToken) && StringUtils.isBlank(paramToken)) {
//            return "login";
//        }
//        String token = StringUtils.isBlank(cookieToken) ? paramToken : cookieToken;
//        MiaoshaUser user = miaoshaUserService.getByToken(response, token);
        model.addAttribute("user", user);
        return "goods_list";
    }
}
