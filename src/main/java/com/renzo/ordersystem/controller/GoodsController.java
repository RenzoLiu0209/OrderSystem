package com.renzo.ordersystem.controller;

import com.renzo.ordersystem.domain.MiaoshaUser;
import com.renzo.ordersystem.redis.GoodsKey;
import com.renzo.ordersystem.redis.RedisService;
import com.renzo.ordersystem.result.CodeMsg;
import com.renzo.ordersystem.result.Result;
import com.renzo.ordersystem.service.GoodsService;
import com.renzo.ordersystem.service.MiaoshaUserService;
import com.renzo.ordersystem.vo.GoodsDetailVo;
import com.renzo.ordersystem.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    RedisService redisService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String toList(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user) {
        model.addAttribute("user", user);
        String html = redisService.get(GoodsKey.getGoodsList,"", String.class);
        if (StringUtils.isNotBlank(html)) {
            return html;
        }
        List<GoodsVo> goodList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodList);
//        return "goods_list";
        WebContext wc = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", wc);
        if (StringUtils.isNotBlank(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        return html;
    }

    @RequestMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public Result toDetail(MiaoshaUser user, @PathVariable("goodsId") long goodsId) {
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainSeconds = 0;

        if (now < startAt) {
            miaoshaStatus = 0;
            remainSeconds = (int)((startAt - now) / 1000);
        } else if (now > endAt) {
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        GoodsDetailVo goodsDetailVo = new GoodsDetailVo();
        goodsDetailVo.setGoods(goods);
        goodsDetailVo.setMiaoshaStatus(miaoshaStatus);
        goodsDetailVo.setRemainSeconds(remainSeconds);
        goodsDetailVo.setMiaoshaUser(user);
        return Result.success(CodeMsg.SUCCESS, goodsDetailVo);
    }

//    @RequestMapping(value = "/to_detail2/{goodsId}", produces = "text/html")
//    @ResponseBody
//    public String toDetail2(HttpServletRequest request, HttpServletResponse response,
//                           Model model, MiaoshaUser user, @PathVariable("goodsId") long goodsId) {
//        // snowflake
//        model.addAttribute("user", user);
//        String html = redisService.get(GoodsKey.getGoodsList,"" + goodsId, String.class);
//        if (StringUtils.isNotBlank(html)) {
//            return html;
//        }
//
//        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
//        model.addAttribute("goods", goods);
//
//        long startAt = goods.getStartDate().getTime();
//        long endAt = goods.getEndDate().getTime();
//        long now = System.currentTimeMillis();
//
//        int miaoshaStatus = 0;
//        int remainSeconds = 0;
//
//        if (now < startAt) {
//            miaoshaStatus = 0;
//            remainSeconds = (int)((startAt - now) / 1000);
//        } else if (now > endAt) {
//            miaoshaStatus = 2;
//            remainSeconds = -1;
//        } else {
//            miaoshaStatus = 1;
//            remainSeconds = 0;
//        }
//        model.addAttribute("miaoshaStatus", miaoshaStatus);
//        model.addAttribute("remainSeconds", remainSeconds);
//
//        WebContext wc = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
//        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", wc);
//        if (StringUtils.isNotBlank(html)) {
//            redisService.set(GoodsKey.getGoodsDetail, ""+goodsId, html);
//        }
//        return html;
//    }
}
