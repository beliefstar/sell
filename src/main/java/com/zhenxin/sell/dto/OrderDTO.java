package com.zhenxin.sell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhenxin.sell.dataobject.OrderDetail;
import com.zhenxin.sell.enums.OrderStatusEnum;
import com.zhenxin.sell.enums.PayStatusEnum;
import com.zhenxin.sell.utils.EnumUtil;
import com.zhenxin.sell.utils.serializer.DateStampFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL) 已在配置文件设置
public class OrderDTO {
    /** 订单ID */
    private String orderId;

    /** 买家名字 */
    private String buyerName;

    /** 买家电话 */
    private String buyerPhone;

    /** 买家地址 */
    private String buyerAddress;

    /** 买家微信ID */
    private String buyerOpenid;

    /** 订单总金额 */
    private BigDecimal orderAmount;

    /**
     * 订单状态
     * 默认0新下单
     */
    private Integer orderStatus;

    /**
     * 支付状态
     * 默认0未支付
     */
    private Integer payStatus;
    /**
     * 创建时间
     */
    @JsonSerialize(using = DateStampFormat.class)
    private Date createTime;
    /**
     * 修改时间
     */
    @JsonSerialize(using = DateStampFormat.class)
    private Date updateTime;

    private List<OrderDetail> orderDetailList;

    @JsonIgnore
    public String getOrderStatusEnum() {
        OrderStatusEnum anEnum = EnumUtil.getMessageByCode(orderStatus, OrderStatusEnum.class);
        if (anEnum != null) {
            return anEnum.getMessage();
        }
        return "";
    }

    @JsonIgnore
    public String getPayStatusEnum() {
        PayStatusEnum anEnum = EnumUtil.getMessageByCode(payStatus, PayStatusEnum.class);
        if (anEnum != null) {
            return anEnum.getMessage();
        }
        return "";
    }
}
