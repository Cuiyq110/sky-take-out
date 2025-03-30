package com.sky.service;

import com.sky.dto.DishDTO;

/**
 * @version V1.0
 * @Title:
 * @Description: 
 * @Copyright 2024 Cuiyq
 * @author: Cuiyq
 * @date: 2025/3/30 11:36
 */

public interface DishService {


    /**
     * 新增菜品和对应的口味
     * @param dishDTO
     */
    void saveWithFlavor(DishDTO dishDTO);

}
