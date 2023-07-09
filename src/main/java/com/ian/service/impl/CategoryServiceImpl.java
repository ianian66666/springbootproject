package com.ian.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ian.entity.Category;
import com.ian.entity.PageBean;
import com.ian.exception.CustomerException;
import com.ian.mapper.CategoryMapper;
import com.ian.mapper.DishMapper;
import com.ian.mapper.SetmealMapper;
import com.ian.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public void save(HttpServletRequest request, Category category) {
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        Long empId = (Long) request.getSession().getAttribute("employee");
        category.setCreateUser(empId);
        category.setUpdateUser(empId);
        categoryMapper.save(category);
    }

    @Override
    public PageBean page(int page, int pageSize) {
        //1.設置分頁參數
        PageHelper.startPage(page, pageSize);
        //2.執行查詢
        List<Category> categoryList = categoryMapper.list();
        Page<Category> p = (Page<Category>) categoryList;
        //封裝pageBean對象
        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        return pageBean;
    }

    @Override
    public void delete(Integer id) {
        //在刪除判斷當前菜品是否已經關聯分類
        int dishcount = dishMapper.flagDish(id);
        if (dishcount > 0) {
            throw new CustomerException("當前分類已關聯菜品，無法刪除");
        }

        //在刪除判斷當前套餐是否已經關聯分類
        int setmealcount = setmealMapper.flagDish(id);
        if (setmealcount > 0) {
            throw new CustomerException("當前分類已關聯套餐，無法刪除");
        }
        categoryMapper.delete(id);
    }

    @Override
    public void update(HttpServletRequest request, Category category) {
        Long empId = (Long) request.getSession().getAttribute("employee");
        category.setUpdateUser(empId);
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);


    }

    @Override
    public List<Category> list(Category category) {
        List<Category> categoryList = categoryMapper.listBytype(category);
        return categoryList;
    }

    @Override
    public List<Category> selectByName(Set<Long> cateID) {
        List<Category>  categoryList =   categoryMapper.selectByName(cateID);
        return categoryList;
    }

    @Override
    public Category getByid(Long categoryId) {
        return categoryMapper.getByid(categoryId);
    }
}
