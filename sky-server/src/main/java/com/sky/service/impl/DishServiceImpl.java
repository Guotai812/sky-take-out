package com.sky.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;


    /**
     *
     * @param dishDTO
     */
    @Override
    @Transactional
    public void saveDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.insert(dish);
        Long id = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            for(DishFlavor f : flavors) {
                f.setDishId(id);
            }
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    @Override
    public PageResult page(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        List<DishVO> dishVOS = dishMapper.PageQuery(dishPageQueryDTO);
        PageInfo<DishVO> pageInfo = new PageInfo<>(dishVOS);
        return new PageResult(pageInfo.getTotal(), dishVOS);
    }

    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {
        for (Long id : ids) {
            Dish dish = dishMapper.queryById(id);
            if (dish.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        List<Long> longs = setMealDishMapper.queryBatch(ids);
        if (longs != null && longs.size() > 0) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        dishMapper.deleteBatch(ids);
        dishFlavorMapper.deleteBatch(ids);
    }

    @Override
    public DishVO findByIdWithFlavors(Long id) {
        Dish dish = dishMapper.queryById(id);
        List<DishFlavor> dishFlavors = dishFlavorMapper.queryById(id);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    @Override
    public void update(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);
        dishFlavorMapper.deleteById(dishDTO.getId());
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            for (DishFlavor f : flavors) {
                f.setDishId(dishDTO.getId());
            }
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    @Override
    public void status(Integer status,Long id) {
        List<SetmealDish> query = setMealDishMapper.queryByDishId(id);
        if (query != null && query.size() > 0) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        Dish dish = new Dish();
        dish.setStatus(status);
        dish.setId(id);
        dishMapper.update(dish);
    }

    @Override
    public List<Dish> queryByCategoryId(Long id) {
        return dishMapper.queryByCategoryId(id);
    }

    @Override
    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> list = dishMapper.list(dish);
        List<DishVO> dishVOS = new ArrayList<>();
        for (Dish d : list) {
            DishVO dishVO = new DishVO();
            List<DishFlavor> dishFlavors = dishFlavorMapper.queryById(d.getId());
            BeanUtils.copyProperties(d, dishVO);
            dishVO.setFlavors(dishFlavors);
            dishVOS.add(dishVO);
        }
        return dishVOS;
    }


}
