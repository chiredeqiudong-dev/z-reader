package com.zreader.setting.api;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.zreader.common.constant.AuthConstant;
import com.zreader.common.response.ApiResponse;
import com.zreader.setting.model.StorageProviderDTO;
import com.zreader.setting.model.StorageProviderVO;
import com.zreader.setting.service.StorageProviderService;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 存储提供商控制器
 * 提供存储提供商管理接口，仅管理员可操作
 *
 * @author zy
 * @date 2025/11/12
 */
@RestController
@RequestMapping("/api/setting/storage")
@SaCheckRole(value = AuthConstant.ROLE_ADMIN_STR)
public class StorageProviderController {

    private final StorageProviderService storageProviderService;

    public StorageProviderController(StorageProviderService storageProviderService) {
        this.storageProviderService = storageProviderService;
    }

    /**
     * 创建存储提供商
     *
     * @param providerDto 存储提供商数据
     * @return 创建结果
     */
    @PostMapping("/add")
    public ApiResponse<Void> createProvider(@RequestBody @Validated StorageProviderDTO providerDto) {
        return storageProviderService.createProvider(providerDto);
    }

    /**
     * 查询所有存储提供商
     *
     * @return 存储提供商列表（脱敏）
     */
    @GetMapping
    public ApiResponse<List<StorageProviderVO>> listProviders() {
        return storageProviderService.listProviders();
    }

    /**
     * 根据ID查询存储提供商
     *
     * @param id 提供商ID
     * @return 存储提供商信息（脱敏）
     */
    @GetMapping("/{id}")
    public ApiResponse<StorageProviderVO> getProviderById(@PathVariable(value = "id") @NotNull(message = "提供商ID不能为空") Integer id) {
        return storageProviderService.getProviderById(id);
    }

    /**
     * 更新存储提供商
     *
     * @param id          提供商ID
     * @param providerDto 存储提供商数据
     * @return 更新结果
     */
    @PutMapping("/update/{id}")
    public ApiResponse<Void> updateProvider(@PathVariable(value = "id") @NotNull(message = "提供商ID不能为空") Integer id, @RequestBody @Validated StorageProviderDTO providerDto) {
        return storageProviderService.updateProvider(id, providerDto);
    }

    /**
     * 删除存储提供商
     *
     * @param id 提供商ID
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public ApiResponse<Void> deleteProvider(@PathVariable(value = "id") @NotNull(message = "提供商ID不能为空") Integer id) {
        return storageProviderService.deleteProvider(id);
    }

    /**
     * 启用/停用存储提供商
     *
     * @param id 提供商ID
     * @return 切换结果
     */
    @PutMapping("/active/{id}")
    public ApiResponse<Void> toggleActive(@PathVariable(value = "id") @NotNull(message = "提供商ID不能为空") Integer id) {
        return storageProviderService.toggleActive(id);
    }
}

