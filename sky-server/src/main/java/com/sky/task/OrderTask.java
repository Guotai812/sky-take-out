package com.sky.task;



import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;


@Component
@Slf4j
public class OrderTask {
    @Autowired
    private OrderMapper orderMapper;

    @Scheduled(cron = "0 * * * * ?")
    public void processTimeOutOrder() {
        log.info("处理超时订单 ");
        List<Orders> orders = orderMapper.queryTimeOut();
        if (orders == null) return;
        for (Orders order : orders) {
            order.setStatus(Orders.CANCELLED);
            order.setCancelTime(LocalDateTime.now());
            order.setCancelReason("订单超时");
            orderMapper.update(order);
        }
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void processDeliveryOrder() {
        log.info("处理派送中的订单");
        List<Orders> orders = orderMapper.queryInDelivery();
        if (orders == null) return;
        for (Orders order : orders) {
            order.setStatus(Orders.COMPLETED);
            orderMapper.update(order);
        }
    }
}
