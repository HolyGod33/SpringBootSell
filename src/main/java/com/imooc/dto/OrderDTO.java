package com.imooc.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.dataobject.OrderDetail;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayStatusEnum;
import com.imooc.util.EnumUtil;
import com.imooc.util.serializer.Date2LongSerializer;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 18:39 2018/10/23
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    //订单Id
    @Id
    private String OrderId;

    //买家名字
    private String buyerName;

    //买家手机号
    private String buyerPhone;

    //买家地址
    private String buyerAddress;

    //买家微信OpenId
    private String buyerOpenId;

    //订单总金额
    private BigDecimal orderAmount;

    //订单状态,默认为0,新下单
    private Integer orderStatus;

    //订单支付状态,默认为0未支付
    private Integer payStatus;

    //创建时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    //更新时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtil.getByCode(orderStatus,OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getByCode(payStatus,PayStatusEnum.class);
    }

}
