package com.renzo.ordersystem.service;

import com.renzo.ordersystem.dao.MiaoshaUserDao;
import com.renzo.ordersystem.domain.MiaoshaUser;
import com.renzo.ordersystem.exception.GlobalException;
import com.renzo.ordersystem.result.CodeMsg;
import com.renzo.ordersystem.result.Result;
import com.renzo.ordersystem.util.MD5Util;
import com.renzo.ordersystem.vo.LoginVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MiaoshaUserService {

    @Resource
    MiaoshaUserDao miaoshaUserDao;

    public void doLogin(LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.USER_NULL);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        MiaoshaUser user = miaoshaUserDao.getById(Long.parseLong(mobile));
        if (user == null) {
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        }

        // check password
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if (calcPass.equals(dbPass)) {
            return;
        } else {
            throw new GlobalException(CodeMsg.PASSWORD_WRONG);
        }
    }
}
