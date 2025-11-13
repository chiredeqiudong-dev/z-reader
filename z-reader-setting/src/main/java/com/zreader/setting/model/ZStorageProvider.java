package com.zreader.setting.model;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 存储提供商实体类
 *
 * @author zy
 * @date 2025/11/12
 */
@TableName("z_storage_provider")
public class ZStorageProvider implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 存储提供商ID
     * 数据库: id INT UNSIGNED PRIMARY KEY
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 提供商名称
     * 数据库: provider_name VARCHAR(32) NOT NULL UNIQUE
     */
    private String providerName;

    /**
     * 存储类型（LOCAL/S3/COS/OSS/KODO）
     * 数据库: storage_type VARCHAR(20) NOT NULL
     */
    private String storageType;

    /**
     * 是否启用 1=启用，0=停用
     * 数据库: is_active TINYINT(1) NOT NULL DEFAULT 0
     */
    private Integer isActive;

    /**
     * 本地绝对路径（仅 local 模式使用）
     * 数据库: local_root_path VARCHAR(512) DEFAULT NULL
     */
    private String localRootPath;

    /**
     * S3 兼容端点
     * 数据库: endpoint VARCHAR(255) DEFAULT NULL
     */
    private String endpoint;

    /**
     * 区域
     * 数据库: region VARCHAR(64) DEFAULT NULL
     */
    private String region;

    /**
     * 访问密钥
     * 数据库: access_key VARCHAR(128) DEFAULT NULL
     */
    private String accessKey;

    /**
     * 私密密钥
     * 数据库: secret_key VARCHAR(256) DEFAULT NULL
     */
    private String secretKey;

    /**
     * 存储桶
     * 数据库: bucket_name VARCHAR(128) DEFAULT NULL
     */
    private String bucketName;

    /**
     * 路径前缀
     * 数据库: base_path VARCHAR(255) DEFAULT '/'
     */
    private String basePath;

    /**
     * 创建时间
     * 数据库: created_at DATETIME(6) NOT NULL DEFAULT ...
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     * 数据库: updated_at DATETIME(6) NOT NULL DEFAULT ... ON UPDATE ...
     */
    private LocalDateTime updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getLocalRootPath() {
        return localRootPath;
    }

    public void setLocalRootPath(String localRootPath) {
        this.localRootPath = localRootPath;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

