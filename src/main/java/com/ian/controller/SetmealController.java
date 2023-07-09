package com.ian.controller;

import com.github.pagehelper.Page;
import com.ian.dto.SetmealDto;
import com.ian.entity.PageBean;
import com.ian.entity.Result;
import com.ian.service.SetmealDishService;
import com.ian.service.SetmealService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐
     *
     * @param setmealDto
     * @return
     */
    @PostMapping
    public Result<String> save(HttpServletRequest request, @RequestBody SetmealDto setmealDto) {

        setmealService.saveWithDish(request, setmealDto);


        return Result.success("成功新增");
    }

    /**
     * 套餐分頁查詢
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result<PageBean> page(int page, int pageSize, String name) {
        log.info("{},{},{}", page, pageSize, name);
        PageBean pageBean = setmealService.page(page, pageSize, name);
        return Result.success(pageBean);


    }
    @DeleteMapping
    public Result<String> deleteById(Long[] ids){
                setmealService.deleteById(ids);

        return Result.success("成功刪除");
    }

    /**
     * 修改前，先將數據查出返回
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<SetmealDto> selectById(@PathVariable Long id){
        SetmealDto setmealDto = setmealService.selectById(id);
        return Result.success(setmealDto);
    }

    /**
     * 修改套餐
     * @param request
     * @param setmealDto
     * @return
     */

    @PutMapping
    public Result<String> updateById(HttpServletRequest request,@RequestBody SetmealDto setmealDto){
        setmealService.updateWithSteDish(request,setmealDto);
        return Result.success("修改成功");

    }
    @PostMapping("/status/{status}")
    public Result<String>updateStatusByIds(HttpServletRequest request,@PathVariable Integer status,Long[] ids){
        setmealService.updateStatusByIds(request,status,ids);

        return Result.success("狀態修改成功");
    }

}
