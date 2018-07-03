package com.zhenxin.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class SellerInfo implements Serializable {
    private static final long serialVersionUID = -8076541536554855695L;
    /** 用户ID */
    @Id
    private String sellerId;

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;

    /** 微信openid */
    private String openid;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
}
