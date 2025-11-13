package com.zreader.setting.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zreader.setting.model.ZStorageProvider;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 存储提供商数据访问
 *
 * @author zy
 * @date 2025/11/12
 */
@Mapper
public interface ZStorageProviderMapper extends BaseMapper<ZStorageProvider> {

    /**
     * 根据提供商名称查询
     */
    default ZStorageProvider selectByProviderName(String providerName) {
        LambdaQueryWrapper<ZStorageProvider> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ZStorageProvider::getProviderName, providerName);
        return selectOne(queryWrapper);
    }

    /**
     * 查询启用的存储提供商（is_active = 1）
     */
    default ZStorageProvider selectActiveProvider() {
        LambdaQueryWrapper<ZStorageProvider> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ZStorageProvider::getIsActive, 1);
        queryWrapper.last("LIMIT 1");
        return selectOne(queryWrapper);
    }
}

