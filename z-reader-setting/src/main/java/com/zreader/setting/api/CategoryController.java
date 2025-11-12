package com.zreader.setting.api;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.zreader.common.constant.AuthConstant;
import com.zreader.common.response.ApiResponse;
import com.zreader.setting.model.CategoryDTO;
import com.zreader.setting.model.CategoryVO;
import com.zreader.setting.service.CategoryService;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 书籍分类控制器
 * 提供分类管理接口，仅管理员可操作
 *
 * @author zy
 * @date 2025/11/12
 */
@RestController
@RequestMapping("/api/setting/category")
@SaCheckRole(value = AuthConstant.ROLE_ADMIN_STR)
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 创建分类
     *
     * @param categoryDto 分类数据
     * @return 创建结果
     */
    @PostMapping("/add")
    public ApiResponse<Void> createCategory(@RequestBody @Validated CategoryDTO categoryDto) {
        return categoryService.createCategory(categoryDto);
    }

    /**
     * 查询所有分类
     *
     * @return 分类列表
     */
    @GetMapping
    public ApiResponse<List<CategoryVO>> listCategories() {
        return categoryService.listCategories();
    }

    /**
     * 更新分类
     *
     * @param id 分类ID
     * @param categoryDto 分类数据
     * @return 更新结果
     */
    @PutMapping("/update/{id}")
    public ApiResponse<Void> updateCategory(@PathVariable(value = "id") @NotNull(message = "分类ID不能为空") Integer id, @RequestBody @Validated CategoryDTO categoryDto) {
        return categoryService.updateCategory(id, categoryDto);
    }

    /**
     * 删除分类
     *
     * @param id 分类ID
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public ApiResponse<Void> deleteCategory(@PathVariable(value = "id") Integer id) {
        return categoryService.deleteCategory(id);
    }
}

