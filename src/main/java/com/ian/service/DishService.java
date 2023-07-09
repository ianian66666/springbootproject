package com.ian.service;

import com.ian.dto.DishDto;
import com.ian.entity.Dish;
import com.ian.entity.PageBean;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface DishService {
    //新增菜品，同時也要插入菜品對應的口味，需要操作兩張表
    public void saveWithFlavor(HttpServletRequest request, DishDto dishDto);

    PageBean page(int page, int pageSize, String name);

    DishDto  selectById(Integer id);

    void updateWithFlavor(HttpServletRequest request, DishDto dishDto);

    void delete(Long[] ids);

    void updateStatus( HttpServletRequest request,Integer status, Long[] ids);

    List<Dish> listByCategory(Dish dish);
}
