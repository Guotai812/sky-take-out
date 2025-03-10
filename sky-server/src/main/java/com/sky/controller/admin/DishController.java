package com.sky.controller.admin;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关借口")
@Slf4j
public class DishController {
    @Autowired
    DishService dishService;
    @Autowired
    RedisTemplate redisTemplate;

    @PostMapping
    @ApiOperation("新增菜品")
    public Result saveDish(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品: {}", dishDTO);
        dishService.saveDish(dishDTO);
        redisTemplate.delete("dish_" + dishDTO.getCategoryId());
        return Result.success();
    }

    @GetMapping("page")
    @ApiOperation("分页查询")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("分页查询菜品, {}", dishService);
         return Result.success(dishService.page(dishPageQueryDTO));
    }

    @DeleteMapping
    @ApiOperation("批量删除")
    public Result delete(@RequestParam List<Long> ids) {
        dishService.deleteBatch(ids);
        cleanCache("dish_*");
        return Result.success();
    }

    @GetMapping("{id}")
    @ApiOperation("修改回显")
    public Result<DishVO> findById(@PathVariable Long id) {
        DishVO dishVO = dishService.findByIdWithFlavors(id);
        return Result.success(dishVO);
    }

    @PutMapping
    @ApiOperation("修改菜品")
    public Result update(@RequestBody DishDTO dishDTO) {
        dishService.update(dishDTO);
        cleanCache("dish_*");
        return Result.success();
    }

    @PostMapping("status/{status}")
    public Result status(@PathVariable Integer status, @RequestParam(value = "id") Long id) {
        dishService.status(status,id);
        cleanCache("dish_*");
        return Result.success();
    }

    @GetMapping("list")
    @ApiOperation("分类id查询菜品")
    public Result<List<Dish>> queryByCategoryId(@RequestParam Long categoryId) {
        List <Dish> dishes =  dishService.queryByCategoryId(categoryId);
        return Result.success(dishes);
    }

    private void cleanCache(String pattern) {
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
