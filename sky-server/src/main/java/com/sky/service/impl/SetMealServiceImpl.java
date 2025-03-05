package com.sky.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    private SetMealMapper setmealMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;

    @Override
    public PageResult page(SetmealPageQueryDTO queryDTO) {
        PageHelper.startPage(queryDTO.getPage(), queryDTO.getPageSize());
        List<Setmeal> setMeals = setmealMapper.page(queryDTO);
        PageInfo<Setmeal> pageInfo = new PageInfo<>(setMeals);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    @Transactional
    public void save(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.insert(setmeal);
        List<SetmealDish> setMealDishes = setmealDTO.getSetmealDishes();
        for (SetmealDish setMealDish : setMealDishes) {
            setMealDish.setSetmealId(setmeal.getId());
            setMealDishMapper.insert(setMealDish);
        }
    }

    @Override
    public void status(Integer status, Long id) {
        Setmeal setmeal = new Setmeal();
        setmeal.setStatus(status);
        setmeal.setId(id);
        setmealMapper.update(setmeal);
    }

    @Override
    public SetmealVO query(Long id) {
        Setmeal setmeal = setmealMapper.query(id);
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);

        List<SetmealDish> setmealDishes = setMealDishMapper.query(id);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    @Override
    @Transactional
    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.update(setmeal);

        List<SetmealDish> setMealDishes = setmealDTO.getSetmealDishes();
        setMealDishMapper.deleteBySetMealId(setmealDTO.getId());
        for (SetmealDish setMealDish : setMealDishes) {
            setMealDish.setSetmealId(setmealDTO.getId());
        }
        setMealDishMapper.insertBatch(setMealDishes);
    }
}
