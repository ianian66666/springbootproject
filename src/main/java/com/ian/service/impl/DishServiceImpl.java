package com.ian.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ian.dto.DishDto;
import com.ian.entity.*;
import com.ian.exception.CustomerException;
import com.ian.mapper.DishMapper;
import com.ian.service.CategoryService;
import com.ian.service.DishFlavorService;
import com.ian.service.DishService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增菜品同時也要保存其口味
     *
     * @param dishDto
     */
    @Transactional
    @Override
    public void saveWithFlavor(HttpServletRequest request, DishDto dishDto) {
        //保存菜品的基本信息到菜品表
        Long empId = (Long) request.getSession().getAttribute("employee");

        dishDto.setCreateTime(LocalDateTime.now());
        dishDto.setCreateUser(empId);
        dishDto.setUpdateTime(LocalDateTime.now());
        dishDto.setUpdateUser(empId);
        dishMapper.saveDish(dishDto);

        Long dishId = dishDto.getId();//菜品id


        //保存菜品口味到dishfflavor
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors.forEach((dishFlavor)-> {
                dishFlavor.setDishId(dishId);
                dishFlavor.setCreateTime(LocalDateTime.now());
                dishFlavor.setCreateUser(empId);
                dishFlavor.setUpdateTime(LocalDateTime.now());
                dishFlavor.setUpdateUser(empId);
        });
        log.info(flavors.toString());
//        for (DishFlavor flavor : flavors) {
//            flavor.setDishId(dishId);
//            flavor.setCreateTime(LocalDateTime.now());
//            flavor.setCreateUser(empId);
//            flavor.setUpdateTime(LocalDateTime.now());
//            flavor.setUpdateUser(empId);
//        }

        dishFlavorService.saveDishFlavor(flavors);


    }

    @Override
    public PageBean page(int page, int pageSize, String name) {
        //1.設置分頁參數
        PageHelper.startPage(page, pageSize);
        //2.執行查詢
        List<DishDto> dishes = dishMapper.list(name);
        Page<DishDto> p = (Page<DishDto>) dishes;


        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());

        return pageBean;
    }

    /**
     * 根據id來查詢菜品信息
     *
     * @param id
     * @return
     */
    @Override
    public DishDto selectById(Integer id) {
        //查詢菜品基本信息
        Dish dish = dishMapper.selectById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);

        //查詢當前菜品對應的口味信息
        List<DishFlavor> dishFlavor = dishFlavorService.selectByDishId(dish.getId());
            dishDto.setFlavors(dishFlavor);
        return dishDto;
    }

    @Transactional
    @Override
    public void updateWithFlavor(HttpServletRequest request, DishDto dishDto) {
        //更新dish表基本資料
        dishDto.setUpdateTime(LocalDateTime.now());
        long empId = (long) request.getSession().getAttribute("employee");
        dishDto.setUpdateUser(empId);
        dishMapper.updateById(dishDto);
            Long[] ids= new Long[1] ;
            ids[0]= dishDto.getId();
        //清除當前菜品的口味資訊
            dishFlavorService.deleteByDishId(ids);

        //添加當前提交過來的口味資訊
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors.forEach(new Consumer<DishFlavor>() {
            @Override
            public void accept(DishFlavor dishFlavor) {
                dishFlavor.setDishId(dishDto.getId());
                dishFlavor.setCreateTime(LocalDateTime.now());
                dishFlavor.setCreateUser(empId);
                dishFlavor.setUpdateTime(LocalDateTime.now());
                dishFlavor.setUpdateUser(empId);
            }
        });
        dishFlavorService.saveDishFlavor(flavors);
    }

    /**
     * 刪除菜品
     * @param ids
     */
    @Override
    public void delete(Long[] ids) {
        Integer status =1;
        //狀態為停售才可以刪除
        int count = dishMapper.selectByStatus(status,ids);
        if(count>0){
            throw  new CustomerException("菜品狀態為啟售，無法刪除");
        }
        //刪除菜品
        dishMapper.delete(ids);
        //刪除口味
        dishFlavorService.deleteByDishId(ids);
    }
    /**
     * 包含批量修改狀態及單個修改狀態
     * @param status
     * @param ids
     * @return
     */
    @Override
    public void updateStatus(HttpServletRequest request,Integer status, Long[] ids) {
        Dish dish = new Dish();
        dish.setStatus(status);
        dish.setUpdateTime(LocalDateTime.now());
        Long empId = (Long) request.getSession().getAttribute("employee");
        dish.setUpdateUser(empId);
        dishMapper.updateStatus(dish,ids);
    }

    @Override
    public List<Dish> listByCategory(Dish dish) {
        dish.setStatus(1);
        List<Dish> list = dishMapper.listByCategory(dish);
        return list;
    }
}
