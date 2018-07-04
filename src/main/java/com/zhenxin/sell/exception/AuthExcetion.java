package com.zhenxin.sell.exception;

import com.zhenxin.sell.enums.ResultEnum;

public class AuthExcetion extends RuntimeException {

    private static final long serialVersionUID = 8965142799217507698L;
    private Integer code;

    public AuthExcetion(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public AuthExcetion(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
