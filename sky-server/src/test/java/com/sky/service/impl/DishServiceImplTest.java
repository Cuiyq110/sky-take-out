package com.sky.service.impl;

import java.math.BigDecimal;

import com.google.common.collect.Lists;

import com.sky.dto.DishDTO;
import com.sky.service.DishService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @version V1.0
 * @Title:
 * @Description:
 * @Copyright 2024 Cuiyq
 * @author: Cuiyq
 * @date: 2025/3/30 12:12
 */
@SpringBootTest
class DishApplicationTest {
    @Resource
    private DishService dishService;

    @Test
    void saveWithFlavor() {
        DishDTO dishDTO = new DishDTO();
        dishDTO.setName("111");
        dishDTO.setCategoryId(200000L);
        dishDTO.setPrice(new BigDecimal("0"));
        dishDTO.setImage("1111");
        dishDTO.setDescription("11111");
        dishDTO.setStatus(1);
        dishDTO.setFlavors(Lists.newArrayList());


         dishService.saveWithFlavor(dishDTO);
    }
}