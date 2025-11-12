package com.zreader.setting.service;

import com.zreader.common.response.ApiResponse;
import com.zreader.setting.model.StorageProviderDTO;
import com.zreader.setting.model.StorageProviderVO;

import java.util.List;

/**
 * 存储提供商服务接口
 *
 * @author zy
 * @date 2025/11/12
 */
public interface StorageProviderService {

    /**
     * 创建存储提供商
     *
     * @param providerDto 存储提供商数据
     * @return 创建结果
     */
    ApiResponse<Void> createProvider(StorageProviderDTO providerDto);

    /**
     * 查询所有存储提供商
     *
     * @return 存储提供商列表（脱敏）
     */
    ApiResponse<List<StorageProviderVO>> listProviders();

    /**
     * 根据ID查询存储提供商
     *
     * @param id 提供商ID
     * @return 存储提供商信息（脱敏）
     */
    ApiResponse<StorageProviderVO> getProviderById(Integer id);

    /**
     * 更新存储提供商
     *
     * @param id 提供商ID
     * @param providerDto 存储提供商数据
     * @return 更新结果
     */
    ApiResponse<Void> updateProvider(Integer id, StorageProviderDTO providerDto);

    /**
     * 删除存储提供商
     *
     * @param id 提供商ID
     * @return 删除结果
     */
    ApiResponse<Void> deleteProvider(Integer id);

    /**
     * 启用/停用存储提供商
     *
     * @param id 提供商ID
     * @return 切换结果
     */
    ApiResponse<Void> toggleActive(Integer id);
}

