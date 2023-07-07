package com.ian.service;

import com.ian.entity.Category;
import com.ian.entity.PageBean;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface CategoryService {
    void save(HttpServletRequest request,Category category);

    PageBean page(int page, int pageSize);

    void delete(Integer id);

    void update(HttpServletRequest request, Category category);

    List<Category> list(Category category);
}
