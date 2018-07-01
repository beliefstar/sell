package com.zhenxin.sell.controller;

import com.zhenxin.sell.VO.ResultVO;
import com.zhenxin.sell.dataobject.SellerInfo;
import com.zhenxin.sell.service.SellerInfoService;
import com.zhenxin.sell.utils.ResultVOUtil;
import com.zhenxin.sell.utils.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/seller/info")
public class SellerInfoController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private SellerInfoService sellerInfoService;

    @GetMapping("/set")
    public void dd(String name, String value) {
        redisService.set(name, value);
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/register")
    @ResponseBody
    public ResultVO register(SellerInfo info) {
        sellerInfoService.register(info);
        return ResultVOUtil.success();
    }

    @PostMapping("/login")
    @ResponseBody
    public ResultVO login(SellerInfo info, HttpServletResponse response) {
        sellerInfoService.login(info, response);
        return ResultVOUtil.success();
    }
}

