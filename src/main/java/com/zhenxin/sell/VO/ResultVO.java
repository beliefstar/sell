package com.zhenxin.sell.VO;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultVO implements Serializable {

    private static final long serialVersionUID = -9173517813994252765L;
    /** 错误码 */
    private Integer code;

    /** 提示信息 */
    private String msg;

    /** 具体内容 */
    private Object data;
}
