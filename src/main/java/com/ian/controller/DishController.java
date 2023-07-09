package com.ian.controller;

import com.ian.dto.DishDto;
import com.ian.entity.Category;
import com.ian.entity.Dish;
import com.ian.entity.PageBean;
import com.ian.entity.Result;
import com.ian.service.DishFlavorService;
import com.ian.service.DishService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public Result<String> save(HttpServletRequest request, @RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        dishService.saveWithFlavor(request,dishDto);
        return  Result.success("新增菜品成功");
    }

    /**
     * 菜品信息的分頁查詢
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result<PageBean> page(int page, int pageSize, String name){
        log.info("{},{},{}", page, pageSize, name);
        PageBean pageBean = dishService.page(page, pageSize, name);
        return Result.success(pageBean);


    }
    /**
     * 根據id來查詢菜品信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<DishDto>  selectById(@PathVariable Integer id){
        DishDto dishDto= dishService.selectById(id);
        return Result.success(dishDto);
    }

    /**
     * 修改菜品
     * @param dishDto
     * @return
     */
    @PutMapping
    public Result<String> update(HttpServletRequest request, @RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        dishService.updateWithFlavor(request,dishDto);
        return  Result.success("新增菜品成功");
    }

    /**
     * 包含批量刪除及單個刪除
     * @param ids
     * @return
     */

    @DeleteMapping
    public Result<String> delete(Long[] ids){
            dishService.delete(ids);
            return Result.success("刪除成功");
    }

    /**
     * 包含批量修改狀態及單個修改狀態
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public Result<String> updateStatus(HttpServletRequest request,@PathVariable Integer status,Long[] ids){
            dishService.updateStatus(request,status,ids);
            return Result.success("修改狀態成功");
    }

    /**
     * 根據條件查詢菜品數據
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public Result<List<Dish>> listByCategory(Dish dish){
        List<Dish> dishList= dishService.listByCategory(dish);
        return Result.success(dishList);
    }
}
