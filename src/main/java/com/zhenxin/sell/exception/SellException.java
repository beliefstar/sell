package com.zhenxin.sell.exception;

import com.zhenxin.sell.enums.ResultEnum;

public class SellException extends RuntimeException {

    private static final long serialVersionUID = -1858808377709446557L;
    private Integer code;

    public SellException(String messge) {
        super(messge);
    }

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
