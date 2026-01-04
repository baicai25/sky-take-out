package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import com.sky.result.PageResult;
import com.sky.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {

    /**
     * 插入订单数据
     * @param orders
     */
    void insert(Orders orders);

    /**
     * 根据订单号查询订单
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);

    /**
     * 历史订单查询
     * @param userId
     * @return
     */
    Page<Orders> getHistoryById(@Param("userId") Long userId,
                                @Param("status") Integer status);

    /**
     * 查询订单详情
     * @param orderId
     * @return
     */
    @Select("select * from orders where id = #{orderId}")
    Orders getOrdersById(Long orderId);

    /**
     * 订单分页查询
     * @param ordersPageQueryDTO
     * @return
     */
    Page<OrderVO> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 各个状态的订单数量统计
     * @return
     */
    Integer getNumsBystatus(Integer status);


    /**
     * ------
     * @param orderId
     * @param status
     * @param cancelReason
     * @param rejectionReason
     * @param cancelTime
     * @param deliveryTime
     */
    void updateStatus(Long orderId, Integer status, String cancelReason , String rejectionReason  , LocalDateTime cancelTime, LocalDateTime  deliveryTime );

    /**
     * 根据订单状态和下单时间查询订单
     * @param status
     * @param orderTime
     */
    //多参数问题,一定要做到属性名和字段名一致,且最好加上@param注解进行说明
    @Select("select * from orders where status = #{status} and order_time < #{orderTime}")
    List<Orders> getByStatusAndOrderTimeL(Integer status, LocalDateTime orderTime);


    /**
     * 根据动态条件统计营业额数据
     * @param map
     * @return
     */
    Double sumByMap(Map map);
}
