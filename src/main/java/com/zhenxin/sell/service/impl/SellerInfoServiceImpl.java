package com.zhenxin.sell.service.impl;

import com.zhenxin.sell.config.SellConfig;
import com.zhenxin.sell.constant.RedisConstant;
import com.zhenxin.sell.dataobject.SellerInfo;
import com.zhenxin.sell.enums.ResultEnum;
import com.zhenxin.sell.exception.AuthExcetion;
import com.zhenxin.sell.repository.SellerInfoRepository;
import com.zhenxin.sell.service.SellerInfoService;
import com.zhenxin.sell.utils.CookieUtil;
import com.zhenxin.sell.utils.KEYUtil;
import com.zhenxin.sell.utils.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class SellerInfoServiceImpl implements SellerInfoService {

    private final SellerInfoRepository sellerInfoRepository;
    private final RedisService redisService;

    @Autowired
    public SellerInfoServiceImpl(SellerInfoRepository sellerInfoRepository, RedisService redisService) {
        this.sellerInfoRepository = sellerInfoRepository;
        this.redisService = redisService;
    }

    @Override
    public void register(SellerInfo info) {
        if (StringUtils.isEmpty(info.getUsername()) || StringUtils.isEmpty(info.getPassword())) {
           throw new AuthExcetion(ResultEnum.PARAM_ERROR);
        }

        String pwd = info.getPassword();
        String nPwd = DigestUtils.md5DigestAsHex(pwd.getBytes(StandardCharsets.UTF_8));
        info.setPassword(nPwd);
        info.setSellerId(KEYUtil.gain());
        info.setOpenid(KEYUtil.gain());
        sellerInfoRepository.save(info);
    }

    @Override
    public void login(SellerInfo info, HttpServletResponse response) {
        if (StringUtils.isEmpty(info.getUsername()) || StringUtils.isEmpty(info.getPassword())) {
            throw new AuthExcetion(ResultEnum.PARAM_ERROR);
        }
        SellerInfo sellerInfo = sellerInfoRepository.findSellerInfoByUsername(info.getUsername());
        if (sellerInfo == null) {
            throw new AuthExcetion(ResultEnum.LOGIN_NOT_EXIST);
        }
        String pwd = DigestUtils.md5DigestAsHex(info.getPassword().getBytes(StandardCharsets.UTF_8));
        if (!sellerInfo.getPassword().equals(pwd)) {
            throw new AuthExcetion(ResultEnum.LOGIN_FAIL);
        }

        String token = RedisConstant.TOKEN_PREFIX;
        Integer expire = RedisConstant.EXPIRE;

        String key = String.format(token, KEYUtil.gain());

        log.info("【redis】key: {}", key);

        redisService.set(key, info, expire.longValue());

        CookieUtil.setCookie(response, SellConfig.LOGIN_COOKIE_NAME, key, expire);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String token = CookieUtil.get(request, SellConfig.LOGIN_COOKIE_NAME);
        if (StringUtils.isEmpty(token)) return;
        redisService.del(token);
        CookieUtil.delete(response, SellConfig.LOGIN_COOKIE_NAME);
    }
}
