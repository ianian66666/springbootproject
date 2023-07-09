package com.ian.service;

import com.ian.entity.DishFlavor;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

public interface DishFlavorService {

    void saveDishFlavor(List<DishFlavor> flavors);

    List<DishFlavor> selectByDishId(Long id);

    void deleteByDishId(Long[] id);
}
