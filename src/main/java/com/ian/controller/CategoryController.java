package com.ian.controller;

import com.ian.entity.Category;
import com.ian.entity.PageBean;
import com.ian.entity.Result;
import com.ian.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 分類頁的添加功能
     *
     * @param request
     * @param category
     * @return
     */

    @PostMapping
    public Result<String> save(HttpServletRequest request, @RequestBody Category category) {
        log.info("添加的分類：{}", category);
        categoryService.save(request, category);
        return Result.success("新增分類");
    }

    /**
     * 分類頁的分頁查詢
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Result<PageBean> page(int page, int pageSize) {
        log.info("{},{},{}", page, pageSize);
        PageBean pageBean = categoryService.page(page, pageSize);
        return Result.success(pageBean);
    }

    /**
     * 刪除分類信息
     *
     * @param id
     * @return
     */
    @DeleteMapping
    public Result<String> delete(Integer id) {
        log.info("刪除分類id為：{}", id);
        categoryService.delete(id);
        return Result.success("刪除分類成功");
    }

    /**
     * 修改分類資料
     *
     * @param request
     * @param category
     * @return
     */
    @PutMapping
    public Result<String> update(HttpServletRequest request, @RequestBody Category category) {
        categoryService.update(request, category);

        return Result.success("修改成功");


    }

    /**
     * 根據條件查詢分類數據
     *
     * @param category
     * @return
     */
    @GetMapping("/list")
    public Result<List<Category>> list(Category category) {

        List<Category> categoryList = categoryService.list(category);

        return  Result.success(categoryList);


    }


}
