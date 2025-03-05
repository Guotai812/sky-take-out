package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/setmeal")
@Api(tags = "套餐相关接口")
public class SetMealController {
    @Autowired
    private SetMealService setMealService;

    @GetMapping("page")
    @ApiOperation("分页查询")
    public Result<PageResult> page(SetmealPageQueryDTO queryDTO){
        PageResult pageResult = setMealService.page(queryDTO);
        return Result.success(pageResult);
    }

    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody SetmealDTO setmealDTO){
        setMealService.save(setmealDTO);
        return Result.success();
    }

    @PostMapping("status/{status}")
    @ApiOperation("起售停售")
    public Result status(@PathVariable Integer status,@RequestParam Long id){
        setMealService.status(status, id);
        return Result.success();
    }

    @GetMapping("{id}")
    @ApiOperation("id查询套餐")
    public Result<SetmealVO> query(@PathVariable Long id){
        SetmealVO setmealVO = setMealService.query(id);
        return Result.success(setmealVO);
    }

    @PutMapping
    @ApiOperation("修改套餐")
    public Result update(@RequestBody SetmealDTO setmealDTO){
        setMealService.update(setmealDTO);
        return Result.success();
    }
}
