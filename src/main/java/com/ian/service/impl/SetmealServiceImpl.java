package com.ian.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ian.dto.DishDto;
import com.ian.dto.SetmealDto;
import com.ian.entity.PageBean;
import com.ian.entity.Setmeal;
import com.ian.entity.SetmealDish;
import com.ian.exception.CustomerException;
import com.ian.mapper.SetmealMapper;
import com.ian.service.SetmealDishService;
import com.ian.service.SetmealService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐，同時需要保存套餐和菜品之間的關聯關係
     * @param setmealDto
     */
    @Transactional
    @Override
    public void saveWithDish(HttpServletRequest request,SetmealDto setmealDto) {
        //保存套餐的基本信息，操作setmeal，執行Insert
        Long empId = (Long) request.getSession().getAttribute("employee");
        setmealDto.setCreateTime(LocalDateTime.now());
        setmealDto.setCreateUser(empId);
        setmealDto.setUpdateTime(LocalDateTime.now());
        setmealDto.setUpdateUser(empId);
            setmealMapper.insert(setmealDto);
        Long setmealId = setmealDto.getId();//套餐id
        List<SetmealDish> setmealDishList = setmealDto.getSetmealDishes();
        setmealDishList.forEach((setmealDish)-> {
            setmealDish.setCreateTime(LocalDateTime.now());
            setmealDish.setCreateUser(empId);
            setmealDish.setUpdateTime(LocalDateTime.now());
            setmealDish.setUpdateUser(empId);
            setmealDish.setSetmealId(setmealId);
        });
        log.info(setmealDishList.toString());


        //保存套餐和菜品之間的關聯關係，操作setmeal_Dish
            setmealDishService.insert(setmealDishList);

    }

    @Override
    public PageBean page(int page, int pageSize, String name) {
        //1.設置分頁參數
        PageHelper.startPage(page, pageSize);
        //2.執行查詢
        List<SetmealDto> dishes = setmealMapper.list(name);
        Page<SetmealDto> p = (Page<SetmealDto>) dishes;


        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());

        return pageBean;
    }

    /**
     * 刪除套餐可以刪除多個及單個
     * @param ids
     */

    @Transactional
    @Override
    public void deleteById(Long[] ids) {
        //先判斷套餐狀態，如是啟用則無法刪除
        Integer status =1;
       int count =  setmealMapper.selectStatus( status,ids);
       log.info("幾個：{}",count);
       //如果不能刪除拋出一個業務異常
        if(count>0){
            throw  new CustomerException("套餐正在售賣中，不能刪除");
        }

       //如果可以刪除先刪除套餐表的數據
        setmealMapper.deleteByIds(ids);


        //刪除關西表中的數據

        setmealDishService.deleteByIds(ids);
    }

    @Override
    public SetmealDto selectById(Long id) {
        SetmealDto setmealDto = new SetmealDto();
        Setmeal setmeal = setmealMapper.selectById(id);
        BeanUtils.copyProperties(setmeal,setmealDto);
        //再取套餐菜品關聯表
        List<SetmealDish> setmealDishList = setmealDishService.selectBySetmealId(id);
        setmealDto.setSetmealDishes(setmealDishList);

        return setmealDto;
    }

    @Transactional
    @Override
    public void updateWithSteDish(HttpServletRequest request,SetmealDto setmealDto) {
        setmealDto.setUpdateTime(LocalDateTime.now());
        Long empId = (Long) request.getSession().getAttribute("employee");
        setmealDto.setUpdateUser(empId);
        setmealMapper.updateWithSteDish(setmealDto);

        //修改套餐及菜品關聯表
        List<SetmealDish> setmealDishList = setmealDto.getSetmealDishes();

            setmealDishService.deleteByIds(new Long[]{setmealDto.getId()});
        setmealDishList.forEach(new Consumer<SetmealDish>() {
            @Override
            public void accept(SetmealDish setmealDish) {
                setmealDish.setUpdateTime(LocalDateTime.now());
                setmealDish.setUpdateUser(empId);
                setmealDish.setCreateTime(LocalDateTime.now());
                setmealDish.setCreateUser(empId);
                setmealDish.setSetmealId(setmealDto.getId());
            }
        });
        setmealDishService.insert(setmealDishList);


    }

    @Override
    public void updateStatusByIds(HttpServletRequest request, Integer status, Long[] ids) {
        Long empId = (Long)request.getSession().getAttribute("employee");
        Setmeal setmeal = new Setmeal();
        setmeal.setStatus(status);
        setmeal.setUpdateTime(LocalDateTime.now());
        setmeal.setUpdateUser(empId);
        setmealMapper.updateStatusByIds(setmeal,ids);
    }
}
