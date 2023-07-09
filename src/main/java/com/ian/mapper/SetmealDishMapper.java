package com.ian.mapper;

import com.ian.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    void insert(List<SetmealDish> setmealDishList);

    void deleteByIds(Long[] ids);

    @Select("SELECT *from setmeal_dish where setmeal_id=#{id}")
    List<SetmealDish> selectBySetmealId(Long id);
}
