package com.sky.mapper;
import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface SetMealDishMapper {

    List<Long> queryBatch(List<Long> ids);

    @Insert("insert into setmeal_dish (setmeal_id, dish_id, name, price, copies) " +
            "values (#{setmealId}, #{dishId}, #{name}, #{price}, #{copies})")
    void insert(SetmealDish setMealDish);

    @Select("select * from setmeal_dish where setmeal_id = #{id}")
    List<SetmealDish> query(Long id);

    @Delete("delete from setmeal_dish where setmeal_id = #{id}")
    void deleteBySetMealId(Long id);

    void insertBatch(List<SetmealDish> setMealDishes);

    @Select("select * from setmeal_dish where dish_id = #{id}")
    List<SetmealDish> queryByDishId(Long id);

    void deleteBatchBySetMealId(List<Long> ids);
}
