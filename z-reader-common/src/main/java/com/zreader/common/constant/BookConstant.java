package com.zreader.common.constant;

/**
 * 书籍相关常量
 *
 * @author zy
 * @date 2025/11/13
 */
public class BookConstant {

    /**
     * 最大文件大小（250MB）
     */
    public static final long MAX_FILE_SIZE = 250 * 1024 * 1024L;

    /**
     * 小文件阈值（50MB）
     */
    public static final long SMALL_FILE_THRESHOLD = 50 * 1024 * 1024L;

    /**
     * 上传状态：待处理
     */
    public static final String UPLOAD_STATUS_PENDING = "PENDING";

    /**
     * 上传状态：上传中
     */
    public static final String UPLOAD_STATUS_UPLOADING = "UPLOADING";

    /**
     * 上传状态：解析中
     */
    public static final String UPLOAD_STATUS_PARSING = "PARSING";

    /**
     * 上传状态：完成
     */
    public static final String UPLOAD_STATUS_COMPLETED = "COMPLETED";

    /**
     * 上传状态：失败
     */
    public static final String UPLOAD_STATUS_FAILED = "FAILED";
}

