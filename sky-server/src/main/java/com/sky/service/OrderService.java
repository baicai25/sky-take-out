package com.sky.service;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {
    /**
     * 用户下单
     * @param ordersSubmitDTO
     * @return
     */
    OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO);

    /**
     * 订单支付
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);

    /**
     * 历史订单查询
     * @param page
     * @param pageSize
     * @param status
     * @return
     */
    PageResult getHistoryById(Integer page, Integer pageSize, Integer status);

    /**
     * 查询订单详情
     * @param orderId
     * @return
     */
    OrderVO orderDetailByOrderId(Long orderId);

    /**
     * 再来一单
     * @param id
     */
    void orderAgain(Long id);


    /**
     * 订单分页查询
     * @param ordersPageQueryDTO
     * @return
     */
    PageResult page(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 各个状态的订单数量统计
     * @return
     */
    OrderStatisticsVO orderStatistics();

    /**
     * 订单接取功能
     * @param id
     */
    void orderAccepet(Long id);

    /**
     * 订单派送开始
     * @param id
     */
    void orderDelivery(Long id);

    /**
     * 订单配送完成
     * @param id
     */
    void orderComplete(Long id);

    /**
     * 取消订单
     * @param ordersCancelDTO
     */
    void orderCancel(OrdersCancelDTO ordersCancelDTO);

    /**
     * 拒绝订单
     * @param ordersRejectionDTO
     */
    void orderRejection(OrdersRejectionDTO ordersRejectionDTO);

    /**
     * 取消订单
     * @param orderId
     */
    void orderCancelPay(Long orderId);
}
