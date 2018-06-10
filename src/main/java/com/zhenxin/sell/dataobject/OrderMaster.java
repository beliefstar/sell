package com.zhenxin.sell.dataobject;

import com.zhenxin.sell.enums.OrderStatusEnum;
import com.zhenxin.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单主表
 * order_master
 */
@Data
@Entity
@DynamicUpdate
public class OrderMaster {

    /** 订单ID */
    @Id
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
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
}
