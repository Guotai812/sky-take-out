package com.sky.controller.user;


import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("userOrderController")
@RequestMapping("user/order/")
@Slf4j
@Api(tags = "订单相关接口")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/submit")
    @ApiOperation("提交订单")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {
        OrderSubmitVO submit = orderService.submit(ordersSubmitDTO);
        return Result.success(submit);
    }

    @PutMapping("/payment")
    @ApiOperation("支付订单")
    public Result<OrderPaymentVO> pay(@RequestBody OrdersPaymentDTO ordersPaymentDTO) {
        OrderPaymentVO pay = orderService.pay(ordersPaymentDTO);
        return Result.success(pay);
    }

    @GetMapping("historyOrders")
    @ApiOperation("历史订单")
    public Result<PageResult> historyOrders(Integer page, Integer pageSize, Integer status) {
        PageResult pageResult = orderService.historyOrder(page, pageSize, status);
        return Result.success(pageResult);
    }

    @GetMapping("/orderDetail/{id}")
    @ApiOperation("查看订单详情")
    public Result<OrderVO> orderDetail(@PathVariable Long id) {
        OrderVO orderVO = orderService.orderDetail(id);
        return Result.success(orderVO);
    }

    @PutMapping("cancel/{id}")
    @ApiOperation("取消订单")
    public Result cancel (@PathVariable Long id) {
        orderService.cancel(id);
        return Result.success();
    }

    @PostMapping("repetition/{id}")
    public Result repetition(@PathVariable Long id) {
        orderService.repetition(id);
        return Result.success();
    }
}
