package com.zhenxin.sell.exception.handler;

import com.zhenxin.sell.VO.ResultVO;
import com.zhenxin.sell.exception.AuthExcetion;
import com.zhenxin.sell.exception.SellException;
import com.zhenxin.sell.utils.ResultVOUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthExcetion.class)
    @ResponseBody
    public ResultVO authHandler(AuthExcetion authExcetion) {
        return ResultVOUtil.error(500, authExcetion.getMessage());
    }

    @ExceptionHandler(SellException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultVO sellException (SellException e) {
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }
}
