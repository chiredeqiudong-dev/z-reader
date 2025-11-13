package com.zreader.setting.service.impl;

import com.zreader.common.response.ApiResponse;
import com.zreader.setting.mapper.ZStorageProviderMapper;
import com.zreader.setting.model.StorageProviderDTO;
import com.zreader.setting.model.StorageProviderVO;
import com.zreader.setting.model.ZStorageProvider;
import com.zreader.setting.service.StorageProviderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.zreader.common.enums.ResultCode.STORAGE_PROVIDER_NAME_EXISTS;
import static com.zreader.common.enums.ResultCode.STORAGE_PROVIDER_NOT_FOUND;

/**
 * 存储提供商服务实现类
 *
 * @author zy
 * @date 2025/11/12
 */
@Service
public class StorageProviderServiceImpl implements StorageProviderService {

    private final ZStorageProviderMapper providerMapper;

    public StorageProviderServiceImpl(ZStorageProviderMapper providerMapper) {
        this.providerMapper = providerMapper;
    }

    @Override
    public ApiResponse<Void> createProvider(StorageProviderDTO providerDto) {
        // 检查提供商名称是否已存在
        ZStorageProvider existingProvider = providerMapper.selectByProviderName(providerDto.getProviderName());
        if (existingProvider != null) {
            return ApiResponse.error(STORAGE_PROVIDER_NAME_EXISTS.getCode(), STORAGE_PROVIDER_NAME_EXISTS.getMsg());
        }

        ZStorageProvider provider = getZStorageProvider(providerDto);

        int result = providerMapper.insert(provider);
        if (result <= 0) {
            return ApiResponse.error();
        }

        return ApiResponse.ok();
    }

    /**
     * 创建新存储提供商
     */
    private ZStorageProvider getZStorageProvider(StorageProviderDTO providerDto) {
        ZStorageProvider provider = new ZStorageProvider();
        provider.setProviderName(providerDto.getProviderName());
        provider.setStorageType(providerDto.getStorageType());
        provider.setIsActive(providerDto.getIsActive() != null ? providerDto.getIsActive() : 0);
        provider.setLocalRootPath(providerDto.getLocalRootPath());
        provider.setEndpoint(providerDto.getEndpoint());
        provider.setRegion(providerDto.getRegion());
        provider.setAccessKey(providerDto.getAccessKey());
        provider.setSecretKey(providerDto.getSecretKey());
        provider.setBucketName(providerDto.getBucketName());
        provider.setBasePath(providerDto.getBasePath() != null && !providerDto.getBasePath().isEmpty() ? providerDto.getBasePath() : "/");
        return provider;
    }

    @Override
    public ApiResponse<List<StorageProviderVO>> listProviders() {
        List<ZStorageProvider> providers = providerMapper.selectList(null);
        List<StorageProviderVO> providerVOList = new ArrayList<>();
        for (ZStorageProvider provider : providers) {
            providerVOList.add(convertToProviderVO(provider));
        }

        return ApiResponse.ok(providerVOList);
    }

    @Override
    public ApiResponse<StorageProviderVO> getProviderById(Integer id) {
        ZStorageProvider provider = providerMapper.selectById(id);
        if (provider == null) {
            return ApiResponse.error(STORAGE_PROVIDER_NOT_FOUND.getCode(), STORAGE_PROVIDER_NOT_FOUND.getMsg());
        }

        return ApiResponse.ok(convertToProviderVO(provider));
    }

    @Override
    public ApiResponse<Void> updateProvider(Integer id, StorageProviderDTO providerDto) {
        // 检查提供商是否存在
        ZStorageProvider provider = providerMapper.selectById(id);
        if (provider == null) {
            return ApiResponse.error(STORAGE_PROVIDER_NOT_FOUND.getCode(), STORAGE_PROVIDER_NOT_FOUND.getMsg());
        }

        // 检查新名称是否与其他提供商重复
        ZStorageProvider existingProvider = providerMapper.selectByProviderName(providerDto.getProviderName());
        if (existingProvider != null && !existingProvider.getId().equals(id)) {
            return ApiResponse.error(STORAGE_PROVIDER_NAME_EXISTS.getCode(), STORAGE_PROVIDER_NAME_EXISTS.getMsg());
        }

        // 更新提供商信息
        provider.setProviderName(providerDto.getProviderName());
        provider.setStorageType(providerDto.getStorageType());
        if (providerDto.getIsActive() != null) {
            provider.setIsActive(providerDto.getIsActive());
        }
        provider.setLocalRootPath(providerDto.getLocalRootPath());
        provider.setEndpoint(providerDto.getEndpoint());
        provider.setRegion(providerDto.getRegion());
        provider.setAccessKey(providerDto.getAccessKey());
        provider.setSecretKey(providerDto.getSecretKey());
        provider.setBucketName(providerDto.getBucketName());
        provider.setBasePath(providerDto.getBasePath());

        int result = providerMapper.updateById(provider);
        if (result <= 0) {
            return ApiResponse.error();
        }

        return ApiResponse.ok();
    }

    @Override
    public ApiResponse<Void> deleteProvider(Integer id) {
        // 检查提供商是否存在
        ZStorageProvider provider = providerMapper.selectById(id);
        if (provider == null) {
            return ApiResponse.error(STORAGE_PROVIDER_NOT_FOUND.getCode(), STORAGE_PROVIDER_NOT_FOUND.getMsg());
        }

        // 删除提供商
        int result = providerMapper.deleteById(id);
        if (result <= 0) {
            return ApiResponse.error();
        }

        return ApiResponse.ok();
    }

    @Override
    public ApiResponse<Void> toggleActive(Integer id) {
        // 检查提供商是否存在
        ZStorageProvider provider = providerMapper.selectById(id);
        if (provider == null) {
            return ApiResponse.error(STORAGE_PROVIDER_NOT_FOUND.getCode(), STORAGE_PROVIDER_NOT_FOUND.getMsg());
        }

        // 切换启用状态
        provider.setIsActive(provider.getIsActive() == 1 ? 0 : 1);
        int result = providerMapper.updateById(provider);
        if (result <= 0) {
            return ApiResponse.error();
        }

        return ApiResponse.ok();
    }

    /**
     * 将存储提供商实体转换为 VO（含脱敏处理）
     *
     * @param provider 存储提供商实体
     * @return 存储提供商VO
     */
    private StorageProviderVO convertToProviderVO(ZStorageProvider provider) {
        StorageProviderVO providerVO = new StorageProviderVO();
        providerVO.setId(provider.getId());
        providerVO.setProviderName(provider.getProviderName());
        providerVO.setStorageType(provider.getStorageType());
        providerVO.setIsActive(provider.getIsActive());
        providerVO.setLocalRootPath(provider.getLocalRootPath());
        providerVO.setEndpoint(provider.getEndpoint());
        providerVO.setRegion(provider.getRegion());
        providerVO.setAccessKey(provider.getAccessKey());
        // 脱敏处理：secretKey 仅返回前 4 位 + *********
        providerVO.setSecretKey(maskSecretKey(provider.getSecretKey()));
        providerVO.setBucketName(provider.getBucketName());
        providerVO.setBasePath(provider.getBasePath());
        providerVO.setCreatedAt(provider.getCreatedAt());
        providerVO.setUpdatedAt(provider.getUpdatedAt());
        return providerVO;
    }

    /**
     * 脱敏处理 secretKey
     * 仅返回前 4 位 + *********
     *
     * @param secretKey 原始密钥
     * @return 脱敏后的密钥
     */
    private String maskSecretKey(String secretKey) {
        if (secretKey == null || secretKey.isEmpty()) {
            return null;
        }
        if (secretKey.length() <= 4) {
            return "*********";
        }
        return secretKey.substring(0, 4) + "*********";
    }
}

