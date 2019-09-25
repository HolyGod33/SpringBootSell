package com.imooc.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 10:26 2019/8/23
 */
@EntityListeners(AuditingEntityListener.class)
@Data
@Entity
@DynamicUpdate
public class SellerInfo {
    @Id
    private String sellerId;

    private String userName;

    private String password;

    private String openid;

    @CreatedDate
    @Column(name = "create_time",updatable = false,nullable = false)
    private Date createTime;

    @LastModifiedDate
    @Column(name = "update_time",updatable = false,nullable = false)
    private Date updateTime;

}
