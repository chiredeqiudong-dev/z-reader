package com.zreader.setting.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * 存储提供商请求 DTO
 *
 * @author zy
 * @date 2025/11/12
 */
public class StorageProviderDTO {

    /**
     * 提供商名称
     */
    @NotBlank(message = "提供商名称不能为空")
    @Size(min = 1, max = 32, message = "提供商名称长度必须在1-32个字符之间")
    private String providerName;

    /**
     * 存储类型（local or cloud）
     */
    @NotBlank(message = "存储类型不能为空")
    @Pattern(regexp = "^(local|cloud)$", message = "存储类型必须是 local or cloud")
    private String storageType;

    /**
     * 是否启用（1=启用，0=停用）
     */
    private Integer isActive;

    /**
     * 本地绝对路径（仅 local 模式使用）
     */
    @Size(max = 512, message = "本地路径长度不能超过512个字符")
    private String localRootPath;

    /**
     * S3 兼容端点
     */
    @Size(max = 255, message = "端点长度不能超过255个字符")
    private String endpoint;

    /**
     * 区域
     */
    @Size(max = 64, message = "区域长度不能超过64个字符")
    private String region;

    /**
     * 访问密钥
     */
    @Size(max = 128, message = "访问密钥长度不能超过128个字符")
    private String accessKey;

    /**
     * 私密密钥
     */
    @Size(max = 256, message = "私密密钥长度不能超过256个字符")
    private String secretKey;

    /**
     * 存储桶
     */
    @Size(max = 128, message = "存储桶名称长度不能超过128个字符")
    private String bucketName;

    /**
     * 路径前缀
     */
    @Size(max = 255, message = "路径前缀长度不能超过255个字符")
    private String basePath;

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
}

