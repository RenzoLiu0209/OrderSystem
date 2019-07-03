package com.renzo.ordersystem.service;

import com.renzo.ordersystem.dao.MiaoshaUserDao;
import com.renzo.ordersystem.domain.MiaoshaUser;
import com.renzo.ordersystem.exception.GlobalException;
import com.renzo.ordersystem.redis.MiaoshaUserKey;
import com.renzo.ordersystem.redis.RedisService;
import com.renzo.ordersystem.result.CodeMsg;
import com.renzo.ordersystem.util.MD5Util;
import com.renzo.ordersystem.util.UUIDUtil;
import com.renzo.ordersystem.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class MiaoshaUserService {

    public static final String COOKIE_NAME_TOEKN = "token";
    @Resource
    MiaoshaUserDao miaoshaUserDao;

    @Autowired
    RedisService redisService;

    public void doLogin(HttpServletResponse response, LoginVo loginVo) {
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
            String token = UUIDUtil.getUuid();
            addCookie(response, token, user);
            return;
        } else {
            throw new GlobalException(CodeMsg.PASSWORD_WRONG);
        }
    }

    public MiaoshaUser getById(HttpServletResponse response, long id) {
        MiaoshaUser user = redisService.get(MiaoshaUserKey.getById, "" + id, MiaoshaUser.class);
        if (user != null) {
            return user;
        }

        user = miaoshaUserDao.getById(id);
        if (user != null) {
            redisService.set(MiaoshaUserKey.getById, "" + id, user);
        }
        return user;
    }

    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.getByToken, token, MiaoshaUser.class);
        // Expand the expired seconds
        if (user != null) {
            addCookie(response, token, user);
        }
        return user;
    }

    public boolean updatePassword(String token, long id, String formPass) {
        MiaoshaUser user = redisService.get(MiaoshaUserKey.getById, "" + id, MiaoshaUser.class);
        if (user == null) {
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        }

        MiaoshaUser toBeUpdatedUser = new MiaoshaUser();
        toBeUpdatedUser.setId(id);
        toBeUpdatedUser.setPassword(MD5Util.formPassToDBPass(formPass, user.getSalt()));
        miaoshaUserDao.updatePass(toBeUpdatedUser);

        redisService.del(MiaoshaUserKey.getById, "" + id);
        user.setPassword(toBeUpdatedUser.getPassword());
        redisService.set(MiaoshaUserKey.getByToken, token, user);
        return true;
    }

    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
        redisService.set(MiaoshaUserKey.getByToken, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOEKN, token);
        cookie.setMaxAge(MiaoshaUserKey.getByToken.getExpireseconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
