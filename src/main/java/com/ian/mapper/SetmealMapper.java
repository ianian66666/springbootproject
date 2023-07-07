package com.ian.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SetmealMapper {
    @Select("SELECT count(*) from setmeal where category_id=#{id}")
    int flagDish(Integer id);
}
