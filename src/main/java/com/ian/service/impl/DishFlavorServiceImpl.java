package com.ian.service.impl;

import com.ian.mapper.DishFlavorMapper;
import com.ian.service.DishFlavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl  implements DishFlavorService {

    @Autowired
    private DishFlavorMapper dishFlavorMapper;
}
