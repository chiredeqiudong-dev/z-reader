package com.zreader.setting.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zreader.setting.model.ZBookCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 书籍分类数据访问
 *
 * @author zy
 * @date 2025/11/12
 */
@Mapper
public interface ZBookCategoryMapper extends BaseMapper<ZBookCategory> {

    /**
     * 根据分类名称查询分类
     */
    default ZBookCategory selectByCategoryName(String categoryName) {
        LambdaQueryWrapper<ZBookCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ZBookCategory::getCategoryName, categoryName);
        return selectOne(queryWrapper);
    }

    /**
     * 查询所有分类
     */
    default List<ZBookCategory> selectAll() {
        LambdaQueryWrapper<ZBookCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(ZBookCategory::getBookCount);
        return selectList(queryWrapper);
    }

    /**
     * 根据 ID 查询分类
     */
    default ZBookCategory selectByCategoryId(Integer id) {
        LambdaQueryWrapper<ZBookCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ZBookCategory::getId, id);
        return selectOne(queryWrapper);
    }
}

