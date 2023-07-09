package com.ian.mapper;

import com.ian.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {



    void saveDishFlavor(List<DishFlavor> flavors);

    @Select("SELECT *from dish_flavor where dish_id =#{id}")
    List<DishFlavor> selectByDishId(Long id);


    void deleteByDishId(Long[] ids);
}
