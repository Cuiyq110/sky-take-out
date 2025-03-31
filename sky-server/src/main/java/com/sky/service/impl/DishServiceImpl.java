package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.annotation.AutoFill;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * @version V1.0
 * @Title:
 * @Description:
 * @Copyright 2024 Cuiyq
 * @author: Cuiyq
 * @date: 2025/3/30 11:37
 */
@Service
public class DishServiceImpl implements DishService {

    @Resource
    private DishMapper dishMapper;
    @Resource
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private Properties pageHelperProperties;

    @Resource
    private SetMealDishMapper setmealDishMapper;

    /**
     * 新增菜品和对应的口味
     *
     * @param dishDTO
     */
    @Override

    public void saveWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
//        向菜品表插入一条数据
        dishMapper.insert(dish);
        Long id = dish.getId();
        if (dishDTO.getFlavors() != null && dishDTO.getFlavors().size() > 0) {
            dishDTO.getFlavors().forEach(dishFlavor -> {
                dishFlavor.setDishId(id);
            });
//        向口味表批量插入多条数据
            dishFlavorMapper.insertBatch(dishDTO.getFlavors());
        }
    }

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */

    @Override
    public PageResult pageQuery1(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        PageResult pageResult = new PageResult(page.getTotal(), page.getResult());
        return pageResult;

    }

    /**
     * 批量删除菜品
     *
     * @param ids
     */
    @Override
    public void deleteBatch(List<Long> ids) {
//        起售中的菜品不能删除
        /**
         *  查询菜品状态，起售中的菜品不能删除
         */
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if (dish.getStatus().equals(StatusConstant.ENABLE)) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

//        被套餐关联的菜品不能删除
        for (Long id : ids) {
            List<Long> setmealIdByDishId = setmealDishMapper.getSetmealIdByDishId(id);
            if (setmealIdByDishId != null && setmealIdByDishId.size() > 0) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
            }
        }

//        删除菜品表中的数据
        dishMapper.deleteBatch(ids);

//        口味数据要删除
        dishFlavorMapper.deleteByDishIds(ids);
    }


    /**
     * 根据id查询菜品和口味数据
     * @param id
     * @return
     */
    @Override
    public DishVO getByIdWithFlavor(Long id) {
//        根据id查菜品
        Dish dish = dishMapper.getById(id);
//        查询口味
        List<DishFlavor> dishFlavorList = dishFlavorMapper.getByDishId(id);
//        将信息传入到dishvo
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish,dishVO);
        dishVO.setFlavors(dishFlavorList);
        return dishVO;
    }

    /**
     * 修改菜品
     * @param dishDTO
     */
    @Override

    public void update(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
//       修改菜品属性
        dishMapper.update(dish);
        /**
         * 删除当前菜品对应的口味数据，重新添加
         */

        //获得菜品id
        Long dishId = dishDTO.getId();
        List<Long> dishIdList = Collections.singletonList(dishId);
//        删除菜品对应的口味数据
        dishFlavorMapper.deleteByDishIds(dishIdList);

        // 判断 flavors 是否非空
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (dishDTO.getFlavors() != null && !dishDTO.getFlavors().isEmpty()) {

            dishDTO.getFlavors().forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
            // 批量插入新的口味数据
            dishFlavorMapper.insertBatch(flavors);
        }

//
    }
}
