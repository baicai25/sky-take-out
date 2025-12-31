package com.sky.controller.admin;


import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.mapper.OrderMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/order")
@RestController
@Slf4j
@Api(tags = "订单管理接口")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 查询订单详情
     * @param id
     * @return
     */
    @ApiOperation("查询订单详情")
    @GetMapping("/details/{id}")
    public Result<OrderVO> list(@PathVariable Long id) {
        log.info("查询订单详情");
        OrderVO list = orderService.orderDetailByOrderId(id);
        return Result.success(list);
    }

    /**
     * 订单分页查询
     * @param ordersPageQueryDTO
     * @return
     */
    @ApiOperation("订单分页查询")
    @GetMapping("/conditionSearch")
    public Result<PageResult> orderSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        log.info("订单分页查询搜索: {}", ordersPageQueryDTO);
        PageResult page = orderService.page(ordersPageQueryDTO);
        return Result.success(page);
    }


    @ApiOperation("各个状态的订单数量统计")
    @GetMapping("/statistics")
    public Result<OrderStatisticsVO> orderStatistics() {
        log.info("各个状态的订单数量统计:      --");
        OrderStatisticsVO orderStatisticsVO = orderService.orderStatistics();
        return Result.success(orderStatisticsVO);
    }


    /**
     * 订单接取功能
     * @param ordersConfirmDTO
     * @return
     */
    @ApiOperation("订单接取功能")
    @PutMapping("/confirm")
    public Result orderAccepet(@RequestBody OrdersConfirmDTO ordersConfirmDTO){
        log.info("订单成功接取:----- ");
        Long id = ordersConfirmDTO.getId();
        orderService.orderAccepet(id);
        return Result.success();
    }

    /**
     * 订单派送开始
     * @param id
     * @return
     */
    @ApiOperation("订单派送功能")
    @PutMapping("/delivery/{id}")
    public Result orderDelivery(@PathVariable Long id){
        log.info("订单派送开始:----- ");
        orderService.orderDelivery(id);
        return Result.success();
    }


    /**
     * 订单配送完成
     * @param id
     * @return
     */
    @ApiOperation("订单配送完成")
    @PutMapping("/complete/{id}")
    public Result orderComplete(@PathVariable Long id){
        log.info("订单配送完成");
        orderService.orderComplete(id);
        return Result.success();
    }


    /**
     * 取消订单
     * @param ordersCancelDTO
     * @return
     */
    @ApiOperation("取消订单")
    @PutMapping("/cancel")
    public Result orderCancel(@RequestBody OrdersCancelDTO ordersCancelDTO){
        log.info("取消订单--:");
        orderService.orderCancel(ordersCancelDTO);
        return Result.success();
    }

    /**
     * 拒绝订单
     * @param ordersRejectionDTO
     * @return
     */
    @ApiOperation("拒绝订单")
    @PutMapping("/rejection")
    public Result orderRejection(@RequestBody OrdersRejectionDTO ordersRejectionDTO){
        log.info("拒绝订单--:");
        orderService.orderRejection(ordersRejectionDTO);
        return Result.success();
    }

}
