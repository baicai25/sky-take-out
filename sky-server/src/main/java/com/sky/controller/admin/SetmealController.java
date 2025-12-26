package com.sky.controller.admin;


import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐管理相关接口")
@Slf4j
public class SetmealController {


    @Autowired
    private SetmealService setmealService;

    @ApiOperation("套餐分页查询")
    @GetMapping("/page")
    //@RequestParam 因为使用了DTO,前端传参和DTO格式相同,不需要使用@RequestParam,
    //@RequestParam一般用于简单参数:( @RequestParam(defaultValue = "1") Integer page,
    //                              @RequestParam(defaultValue = "10") Integer pageSize )
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("分页查询 :{}",setmealPageQueryDTO);
        PageResult pages = setmealService.page(setmealPageQueryDTO);
        return Result.success(pages);
    }

    /**
     * 新增套餐
     * @param setmealDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增套餐")
    @CacheEvict(cacheNames = "setmealCache",key ="#setmealDTO.categoryId")
    public Result save(@RequestBody SetmealDTO setmealDTO){
        log.info("新增套餐: {}",setmealDTO);
        setmealService.save(setmealDTO);
        return Result.success();
    }

    /**
     * 根据id查询套餐
     * @return
     */
    @GetMapping("{id}")
    @ApiOperation("根据id查询套餐")
    public Result<SetmealVO> getById(@PathVariable Long id){
        log.info("根据id查询套餐: {}",id);
        SetmealVO list = setmealService.getByIdWithDish(id);
        return Result.success(list);
    }


    /**
     * 修改套餐
     * @param setmealDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改套餐")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result update(@RequestBody SetmealDTO setmealDTO){
        log.info("修改套餐: {}",setmealDTO);
        setmealService.update(setmealDTO);
        return Result.success();
    }

    /**
     * 根据ids批量删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("根据ids批量删除套餐")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result delete(@RequestParam List<Long> ids) {
        log.info("根据ids批量删除套餐: {}",ids);
        setmealService.delete(ids);
        return Result.success();
    }

    /**
     * 套餐起售停售
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("套餐起售停售接口")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result StartorStop(@PathVariable("status") Integer status, Long id){
        log.info("套餐起售停售: {} , {}",status,id);
        setmealService.StartorStop(status,id);
        return Result.success();
    }

}
