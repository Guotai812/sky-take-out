package com.sky.mapper;


import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface OrderMapper {


    void update(Orders orders);

    void insert(Orders orders);

    List<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);
}
