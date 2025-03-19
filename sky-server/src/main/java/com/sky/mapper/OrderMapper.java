package com.sky.mapper;


import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import com.sky.vo.OrderReportVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {


    Integer update(Orders orders);

    void insert(Orders orders);

    List<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    @Select("select * from orders where id = #{id}")
    Orders queryById(Long id);

    List<Orders> pageQueryAdmin(OrdersPageQueryDTO ordersPageQueryDTO);

    @Select("select count(if(status = 3, status, null)) confirmed, count(if(status = 4, status, null)) deleveryInPProcess from orders")
    OrderReportVO cout();

    @Select("select * from orders where order_time < now() - interval 10 second and pay_status = 0")
    List<Orders> queryTimeOut();

    @Select("select * from orders where status = 4 and order_time < now() - interval 1 hour")
    List<Orders> queryInDelivery();

    @Select("select amount from orders where status = 5 and order_time between #{beginTime} and #{endTime}")
    List<Double> queryTurnover(LocalDateTime beginTime, LocalDateTime endTime);
}
