package com.zreader.setting.service.impl;

import com.zreader.common.response.ApiResponse;
import com.zreader.setting.mapper.ZBookCategoryMapper;
import com.zreader.setting.model.CategoryDTO;
import com.zreader.setting.model.CategoryVO;
import com.zreader.setting.model.ZBookCategory;
import com.zreader.setting.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.zreader.common.enums.ResultCode.*;

/**
 * 书籍分类服务实现类
 *
 * @author zy
 * @date 2025/11/12
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final ZBookCategoryMapper categoryMapper;

    public CategoryServiceImpl(ZBookCategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public ApiResponse<Void> createCategory(CategoryDTO categoryDto) {
        // 检查分类名称是否已存在
        ZBookCategory existingCategory = categoryMapper.selectByCategoryName(categoryDto.getCategoryName());
        if (existingCategory != null) {
            return ApiResponse.error(CATEGORY_NAME_EXISTS.getCode(), CATEGORY_NAME_EXISTS.getMsg());
        }

        // 创建新分类
        ZBookCategory category = new ZBookCategory();
        category.setCategoryName(categoryDto.getCategoryName());
        category.setBookCount(0);

        int result = categoryMapper.insert(category);
        if (result <= 0) {
            return ApiResponse.error();
        }

        return ApiResponse.ok();
    }

    @Override
    public ApiResponse<List<CategoryVO>> listCategories() {
        // 查询所有分类，按 bookCount 降序
        List<ZBookCategory> categories = categoryMapper.selectAll();

        List<CategoryVO> categoryVOList = new ArrayList<>();
        for (ZBookCategory category : categories) {
            categoryVOList.add(convertToCategoryVO(category));
        }

        return ApiResponse.ok(categoryVOList);
    }

    @Override
    public ApiResponse<Void> updateCategory(Integer id, CategoryDTO categoryDto) {
        // 检查分类是否存在
        ZBookCategory category = categoryMapper.selectById(id);
        if (category == null) {
            return ApiResponse.error(CATEGORY_NOT_FOUND.getCode(), CATEGORY_NOT_FOUND.getMsg());
        }

        // 检查新名称是否与其他分类重复
        ZBookCategory existingCategory = categoryMapper.selectByCategoryName(categoryDto.getCategoryName());
        if (existingCategory != null && !existingCategory.getId().equals(id)) {
            return ApiResponse.error(CATEGORY_NAME_EXISTS.getCode(), CATEGORY_NAME_EXISTS.getMsg());
        }

        // 更新分类
        category.setCategoryName(categoryDto.getCategoryName());
        int result = categoryMapper.updateById(category);
        if (result <= 0) {
            return ApiResponse.error();
        }

        return ApiResponse.ok();
    }

    @Override
    public ApiResponse<Void> deleteCategory(Integer id) {
        // 检查分类是否存在
        ZBookCategory category = categoryMapper.selectByCategoryId(id);
        if (category == null) {
            return ApiResponse.error(CATEGORY_NOT_FOUND.getCode(), CATEGORY_NOT_FOUND.getMsg());
        }

        // 检查分类是否正在被使用
        if (category.getBookCount() > 0) {
            return ApiResponse.error(CATEGORY_IN_USE.getCode(), CATEGORY_IN_USE.getMsg());
        }

        // 删除分类
        int result = categoryMapper.deleteById(id);
        if (result <= 0) {
            return ApiResponse.error();
        }

        return ApiResponse.ok();
    }

    /**
     * 将分类实体转换为分类VO
     *
     * @param category 分类实体
     * @return 分类VO
     */
    private CategoryVO convertToCategoryVO(ZBookCategory category) {
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setId(category.getId());
        categoryVO.setCategoryName(category.getCategoryName());
        categoryVO.setBookCount(category.getBookCount());
        categoryVO.setCreatedAt(category.getCreatedAt());
        categoryVO.setUpdatedAt(category.getUpdatedAt());
        return categoryVO;
    }
}

