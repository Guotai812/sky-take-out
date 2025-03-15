package com.sky.mapper;


import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import com.sky.vo.OrderReportVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface OrderMapper {


    void update(Orders orders);

    void insert(Orders orders);

    List<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    @Select("select * from orders where id = #{id}")
    Orders queryById(Long id);

    List<Orders> pageQueryAdmin(OrdersPageQueryDTO ordersPageQueryDTO);

    @Select("select count(if(status = 3, status, null)) confirmed, count(if(status = 4, status, null)) deleveryInPProcess from orders")
    OrderReportVO cout();
}
