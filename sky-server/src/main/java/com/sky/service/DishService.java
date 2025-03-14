package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DishService {
    void saveDish(DishDTO dishDTO);

    PageResult page(DishPageQueryDTO dishPageQueryDTO);

    void deleteBatch(List<Long> ids);

    DishVO findByIdWithFlavors(Long id);

    void update(DishDTO dishDTO);

    void status(Integer status, Long id);

    List<Dish> queryByCategoryId(Long id);

    List<DishVO> listWithFlavor(Dish dish);

}
