package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userShopController")
@RequestMapping("user/shop")
@Api(tags = "店铺相关接口")
@Slf4j
public class ShopController {
    private static final String Key = "SHOP_STATUS";

    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/status")
    @ApiOperation("获取店铺营业状态")
    public Result<Integer> getStatus(){
        Integer o = (Integer) redisTemplate.opsForValue().get(Key);
        log.info("获取店铺状态为, {}", o == 1? "营业中" : "打烊中");
        return Result.success(o);
    }
}
