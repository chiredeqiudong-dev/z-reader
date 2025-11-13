
package com.zreader.common.enums;

/**
 * 存储提供商枚举
 * - LOCAL: 本地存储
 * - S3: AWS S3（兼容 MinIO）
 * - COS: 腾讯云对象存储
 * - OSS: 阿里云对象存储
 * - KODO: 七牛云对象存储
 */
public enum StorageProvider {


    LOCAL("LOCAL", "本地存储"),
    S3("S3", "AWS S3 / 兼容 S3 协议"),
    COS("COS", "腾讯云 COS"),
    OSS("OSS", "阿里云 OSS"),
    KODO("KODO", "七牛云 KODO");

    /**
     * 校验正则（用于 @Pattern）
     */
    public static final String VALID_PATTERN = "^(LOCAL|S3|COS|OSS|KODO)$";

    private final String code;
    private final String description;

    StorageProvider(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}