package com.ian.service.impl;

import com.ian.entity.DishFlavor;
import com.ian.mapper.DishFlavorMapper;
import com.ian.service.DishFlavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishFlavorServiceImpl  implements DishFlavorService {

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Override
    public void saveDishFlavor(List<DishFlavor> flavors) {
            dishFlavorMapper.saveDishFlavor(flavors);
    }

    @Override
    public List<DishFlavor> selectByDishId(Long id) {

        return dishFlavorMapper.selectByDishId(id);
    }

    @Override
    public void deleteByDishId(Long[] ids) {
                dishFlavorMapper.deleteByDishId(ids);
    }
}
