package com.imooc.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author JXUT CXY
 * @Description 使用Jpa创建数据库条目，字段为数据库表
 * @Date: created in 18:34 2018/10/17
 */


@EntityListeners(AuditingEntityListener.class)
//字段映射成数据库表
@Entity
//自动更新时间
@DynamicUpdate
//lombok包，用于自动生成而不需要代码get/settter方法
@Data
public class ProductCategory {

    @Id
    @GeneratedValue
    private Integer categoryId;

    //类目名字
    private String categoryName;

    //类目编号
    private Integer categoryType;

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    public ProductCategory() {
    }

    @CreatedDate
    @Column(name = "create_time",updatable = false,nullable = false)
    private Date createTime;

    @LastModifiedDate
    @Column(name = "update_time",nullable = false)
    private Date updateTime;


    @Override
    public String toString() {
        return "ProductCategory{" +
                "categoryID=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryType=" + categoryType +
                '}';
    }
}
