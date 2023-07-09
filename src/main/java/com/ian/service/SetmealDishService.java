package com.ian.service;

import com.ian.entity.SetmealDish;

import java.util.List;

public interface SetmealDishService {
    void insert(List<SetmealDish> setmealDishList);

    void deleteByIds(Long[] ids);

    List<SetmealDish> selectBySetmealId(Long id);
}
