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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    @PostMapping
    @ApiOperation("新增菜品")
    @CacheEvict(cacheNames = "dishCache", key = "#dishDTO.categoryId")
    public Result saveDish(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品: {}", dishDTO);
        dishService.saveDish(dishDTO);
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
    @CacheEvict(cacheNames = "dishCache", allEntries = true)
    public Result delete(@RequestParam List<Long> ids) {
        dishService.deleteBatch(ids);
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
    @CacheEvict(cacheNames = "dishCache", allEntries = true)
    public Result update(@RequestBody DishDTO dishDTO) {
        dishService.update(dishDTO);
        return Result.success();
    }

    @PostMapping("status/{status}")
    @CacheEvict(cacheNames = "dishCache", allEntries = true)
    public Result status(@PathVariable Integer status, @RequestParam(value = "id") Long id) {
        dishService.status(status,id);
        return Result.success();
    }

    @GetMapping("list")
    @ApiOperation("分类id查询菜品")
    public Result<List<Dish>> queryByCategoryId(@RequestParam Long categoryId) {
        List <Dish> dishes =  dishService.queryByCategoryId(categoryId);
        return Result.success(dishes);
    }
}
