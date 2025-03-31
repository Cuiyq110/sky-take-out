package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @version V1.0
 * @Title:
 * @Description: 菜品口味表持久层接口
 * @Copyright 2024 Cuiyq
 * @author: Cuiyq
 * @date: 2025/3/30 12:03
 */
@Mapper
public interface DishFlavorMapper {

//    批量插入数据
    void insertBatch(List<DishFlavor> dishFlavorList);

    void deleteByDishIds(List<Long> ids);
}
