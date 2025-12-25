package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Employee;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private DishMapper dishMapper;

    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    @Override
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());

        Page<SetmealVO> page = setmealMapper.page(setmealPageQueryDTO);

        Long total = page.getTotal();
        List<SetmealVO> list = page.getResult();
        return new PageResult(total,list);
    }

    /**
     * 新增套餐
     * @param setmealDTO
     */
    @Transactional
    @Override
    public void save(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);

        //插入套餐基本属性
        setmealMapper.insert(setmeal);

        Long setmealId = setmeal.getId();
      //  Long dishId = ;  dishId好像和菜品名称自动对应上了,不需要赋值

        List<SetmealDish> setDish = setmealDTO.getSetmealDishes();

        if(setDish != null && setDish.size()>0 ){
            setDish.forEach(sd -> {
                sd.setSetmealId(setmealId);
      //          sd.setDishId();   同上
            });
            //批量插入套餐中菜品
            setmealDishMapper.insertBatch(setDish);
        }
    }

    /**
     * 根据id查询当前套餐
     * @param id
     * @return
     */
    @Override
    public SetmealVO getByIdWithDish(Long id) {
        Setmeal setmeal = setmealMapper.getSmlById(id);

        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal,setmealVO);

        List<SetmealDish> setmealDish = setmealDishMapper.getStDishById((id));

        setmealVO.setSetmealDishes(setmealDish);

        return setmealVO;
    }


    /**
     * 修改套餐
     * @param setmealDTO
     */
    @Transactional
    @Override
    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.update(setmeal);
        //修改就是先删除再插入

        setmealDishMapper.deleteById(setmeal.getId());

        Long setmealId = setmeal.getId();
        List<SetmealDish> setDish = setmealDTO.getSetmealDishes();

        if(setDish != null && setDish.size()>0 ){
            setDish.forEach(sd -> {
                sd.setSetmealId(setmealId);
            });
            //批量插入套餐中菜品
            setmealDishMapper.insertBatch(setDish);
        }
    }


    @Transactional  //事务管理,防止只删除部分产生脏数据
    @Override
    public void delete(List<Long> ids) {
        //判断当前套餐能否删除---是否处于起售中
        for (Long id : ids) {
            Setmeal setmeal = setmealMapper.getSmlById(id);
            if (setmeal.getStatus() == StatusConstant.ENABLE) {
                //处于售卖状态,不能删除
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }
        setmealMapper.deleteByids(ids);
        setmealDishMapper.deleteByIds(ids);
    }


    @Override
    public void StartorStop(Integer status, Long id) {
        //查当前套餐里面有没有没有起售的菜,菜没起售套餐也不能起售
        List<SetmealDish> dishes = setmealDishMapper.getStDishById(id);

        for (SetmealDish dish : dishes) {
            Dish dis = dishMapper.getById(dish.getDishId());
            if (dis.getStatus() == StatusConstant.DISABLE && setmealMapper.getSmlById(id).getStatus()==StatusConstant.DISABLE) {
                //菜品处于未起售状态,套餐也不能起售
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ENABLE_FAILED);
            }
        }



        Setmeal setmeal = Setmeal.builder()
                .id(id).status(status).build();

        setmealMapper.update(setmeal);
    }

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }
}
