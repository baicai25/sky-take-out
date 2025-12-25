package com.sky.controller.user;


import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("userShopController")
@RequestMapping("/user/shop")
@Slf4j
@Api(tags = "店铺相关接口")
public class ShopController {

    private static final String KEY = "Shop Status";

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     *
     * @return
     */
    @GetMapping("/status")
    @ApiOperation("获取当前的营业状态")
    public Result getStatus(){
        //强转的原因是使用Redis的String类型,会将传入的数据转为String,拿出来也是string,这里强转改为Interger,便于接受
        Integer shopStatus = (Integer) redisTemplate.opsForValue().get("KEY");
        log.info("获取当前的营业状态: {}",shopStatus == 1 ? "营业中" : "打烊了");
        return Result.success(shopStatus);
    }

}
