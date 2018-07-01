package com.zhenxin.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    PARAM_ERROR(0, "参数不正确"),

    CART_EMPTY(1, "购物车为空"),

    PRODUCT_NOT_EXIST(10, "商品不存在"),

    LACK_OF_STOCK(11, "商品库存不足"),

    ORDER_NOT_EXIST(12, "订单信息不存在"),

    ORDERDETAIL_NOT_EXIST(13, "订单详情不存在"),

    ORDER_STATUS_ERROR(14, "订单状态不正确"),

    ORDER_DETAIL_EMPTY(16, "订单为空"),

    ORDER_UPDATE_FAIL(17, "订单更新失败"),

    ORDER_PAY_STATUS_ERROR(18, "订单支付状态不正确"),

    ORDER_OWNER_ERROR(19, "该订单不属于当前用户"),

    OPTION_SUCCESS(20, "操作成功！"),

    PRODUCT_STATUS_ERROR(21, "商品状态错误！"),

    OPTION_ERROR(22, "操作失败！"),

    CATEGORY_IS_EXIST(23, "类目编号已存在"),

    LOGIN_FAIL(24, "帐号或密码错误"),

    LOGIN_SUCCESS(25, "登录成功"),

    REGISTER_FAIL(24, "注册失败"),

    REGISTER_SUCCESS(25, "注册成功"),

    LOGIN_NOT_EXIST(26, "用户不存在")
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
