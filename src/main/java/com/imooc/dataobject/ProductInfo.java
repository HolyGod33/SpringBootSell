package com.imooc.dataobject;

import com.imooc.enums.ProductStatusEnum;
import com.imooc.util.EnumUtil;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 20:12 2018/10/18
 */

@EntityListeners(AuditingEntityListener.class)
@Entity
@DynamicUpdate
@Data
public class ProductInfo implements Serializable
{
    private static final long serialVersionUID = -3001981883419578702L;
    @Id
    private String  productId;

    //名字
    private String productName;

    //单价
    private BigDecimal productPrice;

    //库存
    private Integer productStock;

    //描述
    private String productDescription;

    //小图
    private String productIcon;

    //状态 0正常1下架
    private Integer productStatus = ProductStatusEnum.UP.getCode();

    //类目编号
    private Integer categoryType;

    //创建时间
    @CreatedDate
    @Column(name = "create_time",updatable = false,nullable = false)
    private Date createTime;

    //修改时间
    @LastModifiedDate
    @Column(name = "update_time",nullable = false)
    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnum getProductStateEnum(){
        return EnumUtil.getByCode(productStatus,ProductStatusEnum.class);
    }


}
