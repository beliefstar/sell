package com.zhenxin.sell.intercept;

import com.zhenxin.sell.config.ConfigProperties;
import com.zhenxin.sell.utils.CookieUtil;
import com.zhenxin.sell.utils.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthIntercept implements HandlerInterceptor {

    @Autowired
    private RedisService redisService;

    @Autowired
    private ConfigProperties sellConfig;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String token = CookieUtil.get(httpServletRequest, "token");
        String rUrl = httpServletRequest.getRequestURL().toString();
        if (!StringUtils.isEmpty(token)) {
            Object info = redisService.get(token);
            if (info != null) {
                return true;
            }
        }

        httpServletResponse.sendRedirect(sellConfig.getAppServer() + "/sell/seller/info/login?redirectUrl=" + rUrl);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
