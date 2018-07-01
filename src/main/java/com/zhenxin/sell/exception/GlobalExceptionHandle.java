package com.zhenxin.sell.exception;

import com.zhenxin.sell.VO.ResultVO;
import com.zhenxin.sell.utils.ResultVOUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(AuthExcetion.class)
    @ResponseBody
    public ResultVO authHandle(AuthExcetion authExcetion) {
        return ResultVOUtil.error(500, authExcetion.getMessage());
    }

}
