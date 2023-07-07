package com.ian.service.impl;

import com.ian.mapper.DishMapper;
import com.ian.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
}
