package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 根据菜品id查询套餐id
     * @param dishIds
     * @return
     */
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

    /**
     *  批量插入套餐菜品
     * @param setDish
     */
    void insertBatch(List<SetmealDish> setDish);

    /**
     * 根据id查询套餐内菜品信息
     * @param setmealId
     * @return
     */
    @Select("select * from setmeal_dish where setmeal_id = #{setmealId}")
    List<SetmealDish> getStDishById(Long setmealId);

    /**
     * 根据ids批量删除套餐中的菜品数据(不是菜品)
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    /**
     * 根据id删除套餐中的菜品数据
     * @param id
     */
    @Delete("delete from setmeal_dish where  setmeal_id = #{id}")
    void deleteById(Long id);
}
