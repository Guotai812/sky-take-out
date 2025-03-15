package com.sky.service;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderReportVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {
    OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO);

    OrderPaymentVO pay(OrdersPaymentDTO ordersPaymentDTO);

    PageResult historyOrder(Integer page, Integer pageSize, Integer status);

    OrderVO orderDetail(Long id);

    void cancel(Long id);

    void repetition(Long id);

    PageResult pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    void confirm(OrdersConfirmDTO ordersConfirmDTO);

    OrderReportVO statistics();

    void cancel (OrdersCancelDTO ordersCancelDTO);

    void reject(OrdersRejectionDTO ordersRejectionDTO);
}
