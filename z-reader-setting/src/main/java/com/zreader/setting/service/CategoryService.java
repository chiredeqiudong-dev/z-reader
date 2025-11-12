package com.zreader.setting.service;

import com.zreader.common.response.ApiResponse;
import com.zreader.setting.model.CategoryDTO;
import com.zreader.setting.model.CategoryVO;

import java.util.List;

/**
 * 书籍分类服务接口
 *
 * @author zy
 * @date 2025/11/12
 */
public interface CategoryService {

    /**
     * 创建分类
     *
     * @param categoryDto 分类数据
     * @return 创建结果
     */
    ApiResponse<Void> createCategory(CategoryDTO categoryDto);

    /**
     * 查询所有分类
     *
     * @return 分类列表（按 bookCount 倒序）
     */
    ApiResponse<List<CategoryVO>> listCategories();

    /**
     * 更新分类
     *
     * @param id 分类ID
     * @param categoryDto 分类数据
     * @return 更新结果
     */
    ApiResponse<Void> updateCategory(Integer id, CategoryDTO categoryDto);

    /**
     * 删除分类
     *
     * @param id 分类ID
     * @return 删除结果
     */
    ApiResponse<Void> deleteCategory(Integer id);
}

