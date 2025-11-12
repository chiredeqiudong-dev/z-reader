package com.zreader.setting.model;

import java.time.LocalDateTime;

/**
 * 存储提供商响应 VO
 * 敏感信息（secretKey）会被脱敏处理
 *
 * @author zy
 * @date 2025/11/12
 */
public class StorageProviderVO {

    /**
     * 存储提供商ID
     */
    private Integer id;

    /**
     * 提供商名称
     */
    private String providerName;

    /**
     * 存储类型
     */
    private String storageType;

    /**
     * 是否启用
     */
    private Integer isActive;

    /**
     * 本地绝对路径
     */
    private String localRootPath;

    /**
     * S3 兼容端点
     */
    private String endpoint;

    /**
     * 区域
     */
    private String region;

    /**
     * 访问密钥
     */
    private String accessKey;

    /**
     * 私密密钥（脱敏后）
     */
    private String secretKey;

    /**
     * 存储桶
     */
    private String bucketName;

    /**
     * 路径前缀
     */
    private String basePath;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
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

