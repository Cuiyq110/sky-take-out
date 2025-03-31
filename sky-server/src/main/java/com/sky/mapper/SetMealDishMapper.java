package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @version V1.0
 * @Title:
 * @Description:
 * @Copyright 2024 Cuiyq
 * @author: Cuiyq
 * @date: 2025/3/31 09:43
 */
@Mapper
public interface SetMealDishMapper {


    /*
        根据菜品id查询套餐id
     */
    List<Long> getSetmealIdByDishId(Long id);
}
