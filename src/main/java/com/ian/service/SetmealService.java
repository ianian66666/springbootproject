package com.ian.service;

import com.ian.dto.SetmealDto;
import com.ian.entity.PageBean;
import jakarta.servlet.http.HttpServletRequest;

public interface SetmealService {


    public void  saveWithDish(HttpServletRequest request,SetmealDto setmealDto);

    PageBean page(int page, int pageSize, String name);


    void deleteById(Long[] ids);

    SetmealDto selectById(Long id);

    void updateWithSteDish(HttpServletRequest request,SetmealDto setmealDto);

    void updateStatusByIds(HttpServletRequest request, Integer status, Long[] ids);
}
