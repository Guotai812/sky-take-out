package com.sky.controller.admin;


import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("adminOrderController")
@RequestMapping("admin/order")
@Slf4j
@Api(tags = "商家订单相关接口")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("conditionSearch")
    @ApiOperation("查询订单")
    public Result<PageResult> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO) {
        PageResult pageResult = orderService.pageQuery(ordersPageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("details/{id}")
    @ApiOperation("订单详情")
    public Result<OrderVO> detail(@PathVariable Long id) {
        OrderVO orderVO = orderService.orderDetail(id);
        return Result.success(orderVO);
    }

    @GetMapping("statistics")
    @ApiOperation("订单数量统计")
    public Result<OrderReportVO> statistics() {
        OrderReportVO orderReportVO = orderService.statistics();
        return Result.success(orderReportVO);
    }

    @PutMapping("confirm")
    @ApiOperation("接单")
    public Result confirm(@RequestBody OrdersConfirmDTO ordersConfirmDTO) {
        orderService.confirm(ordersConfirmDTO);
        return Result.success();
    }

    @PutMapping("cancel")
    @ApiOperation("取消订单")
    public Result cancel(@RequestBody OrdersCancelDTO ordersCancelDTO) {
        orderService.cancel(ordersCancelDTO);
        return Result.success();
    }

    @PutMapping("rejection")
    @ApiOperation("拒绝订单")
    public Result cancel(@RequestBody OrdersRejectionDTO ordersRejectionDTO) {
        orderService.reject(ordersRejectionDTO);
        return Result.success();
    }

    @PutMapping("delivery/{id}")
    @ApiOperation("派送")
    public Result cancel(@PathVariable Long id) {
        orderService.deliver(id);
        return Result.success();
    }

    @PutMapping("complete/{id}")
    @ApiOperation("完成")
    public Result complete(@PathVariable Long id) {
        orderService.complete(id);
        return Result.success();
    }

    @GetMapping("reminder/{id}")
    @ApiOperation("客户催单")
    public Result remind(@PathVariable Long id) {
        orderService.remind(id);
        return Result.success();
    }
}
