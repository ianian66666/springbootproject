package com.ian.service.impl;

import com.ian.entity.SetmealDish;
import com.ian.mapper.SetmealDishMapper;
import com.ian.service.SetmealDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetmealDishServiceImpl implements SetmealDishService {

    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Override
    public void insert(List<SetmealDish> setmealDishList) {
        setmealDishMapper.insert(setmealDishList);
    }

    @Override
    public void deleteByIds(Long[] ids) {
        setmealDishMapper.deleteByIds(ids);
    }

    @Override
    public List<SetmealDish> selectBySetmealId(Long id) {
        return setmealDishMapper.selectBySetmealId(id);
    }
}
