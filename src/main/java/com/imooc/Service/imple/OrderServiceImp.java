package com.imooc.Service.imple;

import com.imooc.Service.*;
import com.imooc.converter.OrderMaster2OrderDTOConverter;
import com.imooc.dataobject.OrderDetail;
import com.imooc.dataobject.OrderMaster;
import com.imooc.dataobject.ProductInfo;
import com.imooc.dto.CartDTO;
import com.imooc.dto.OrderDTO;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayStatusEnum;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.ResponseBankException;
import com.imooc.exception.SellException;
import com.imooc.repository.OrderDetailRepository;
import com.imooc.repository.OrderMasterRepository;
import com.imooc.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.imooc.converter.OrderMaster2OrderDTOConverter.convert;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 18:54 2018/10/23
 */
@Service
@Slf4j
public class OrderServiceImp implements OrderService {
    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private PayService payService;

    @Autowired
    private PushMessage pushMessage;

    @Autowired
    private WebSocket webSocket;


    BigDecimal orderAmount=new BigDecimal(BigInteger.ZERO);

//    List<CartDTO> cartDTOList=new ArrayList<>();


    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId=KeyUtil.genUniqueKey();

        //1.查询商品数据(数量,单价)
        for (OrderDetail orderDetail: orderDTO.getOrderDetailList()){
            ProductInfo productInfo=productService.findOne(orderDetail.getProductId());
            if(productInfo==null){
//                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
                throw new ResponseBankException();
            }

            //2.计算总价格
            orderAmount=productInfo.getProductPrice()
                        .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmount);


            //订单详情入库
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail) ;
            orderDetailRepository.save(orderDetail);

//            CartDTO cartDTO=new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());
//            cartDTOList.add(cartDTO);
        }

        //3.写入订单数据库(orderMaster和orderDetail)
        OrderMaster orderMaster=new OrderMaster();

        orderDTO.setOrderAmount(orderAmount);
        orderDTO.setOrderId(orderId);
        orderDTO.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderDTO.setPayStatus(PayStatusEnum.WAIT.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMasterRepository.save(orderMaster);

        //4.扣库存
        List<CartDTO> cartDTOList=new ArrayList<>();
        orderDTO.getOrderDetailList().stream()
                .map(e->new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());

        productService.decreaseStock(cartDTOList);

        //发送websocket消息
        webSocket.sendMessage("有新的订单");

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster=orderMasterRepository.findOne(orderId);
        if(orderMaster==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST); }

        List<OrderDetail> orderDetailList=orderDetailRepository.findByOrderId(orderId);

        if (CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDERDeatail_NOTEXIST);
        }

        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyOpenId, Pageable pageable) {

        Page<OrderMaster> orderMasterPage=orderMasterRepository.findBybuyerOpenId(buyOpenId,pageable);
        List<OrderDTO> orderDTOList=OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());

    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);

        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("取消订单 订单状态不正确 orderId={}",orderDTO.getOrderId());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster orderMasterUpdate=orderMasterRepository.save(orderMaster);
        if(orderMasterUpdate.getOrderStatus()==null){
            log.error("[取消订单失败] orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //返回库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[取消订单] 该订单内没有商品 orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList=orderDTO.getOrderDetailList().stream()
                .map(e-> new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        //如果已支付,需要他退款
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO
            payService.refund(orderDTO);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判读订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("[完结订单] 订单状态不正确 orderId={}",orderDTO.getOrderId());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        OrderMaster orderMaster=new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster result=orderMasterRepository.save(orderMaster);
        if(result.getOrderStatus()==null){
            log.error("[完结订单失败] orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //推送微信消息
        pushMessage.orderStatus(orderDTO);
        return  orderDTO;


    }


    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断支付状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("[订单支付完成] 订单状态不正确 orderId={}",orderDTO.getOrderId());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("[订单支付完成] 订单支付不正确,orderId={}",orderDTO.getOrderId());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS);
        }
        //修改支付状态
        OrderMaster orderMaster=new OrderMaster();
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster result=orderMasterRepository.save(orderMaster);
        if(result.getPayStatus()==null){
            log.error("[订单支付完成] 更新失败 orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return  orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasters = orderMasterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList=OrderMaster2OrderDTOConverter.convert(orderMasters.getContent());
        return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasters.getTotalElements());
    }
}
