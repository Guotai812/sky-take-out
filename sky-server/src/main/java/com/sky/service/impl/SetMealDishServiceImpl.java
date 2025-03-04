package com.sky.service.impl;

import com.sky.mapper.SetMealDishMapper;
import com.sky.service.SetMealDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetMealDishServiceImpl implements SetMealDishService {
    @Autowired
    private SetMealDishMapper setMealDishMapper;
}
