package com.sky.mapper;



import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface SetMealDishMapper {



    List<Long> queryBatch(List<Long> ids);
}
