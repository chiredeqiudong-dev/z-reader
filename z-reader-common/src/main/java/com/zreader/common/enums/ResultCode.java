package com.zreader.common.enums;


/**
 * 统一响应状态码枚举
 */
public enum ResultCode {

    /**
     * 通用
     */
    SUCCESS("2000", "success"),
    ERROR("5000", "error"),
    SYSTEM_ERROR("S1000", "系统未知错误"),
    INVALID_PARAM("S1001", "参数错误"),
    RESOURCE_NOT_FOUND("S1002", "资源不存在"),

    /**
     * 认证授权相关 A1xxx
     */
    AUTH_LOGIN_FAILED("A1001", "用户名或密码错误"),
    AUTH_USERNAME_EXISTS("A1003", "用户名已存在"),
    AUTH_NOT_LOGIN("A1004", "未登录"),
    AUTH_NO_PERMISSION("A1005", "无权限访问"),
    AUTH_ONLY_ADMIN("A1007", "仅管理员可操作"),

    /**
     * 用户相关 U2xxx
     */
    USER_NOT_FOUND("U2001", "用户不存在"),

    /**
     * 设置模块相关 S3xxx
     */
    CATEGORY_NAME_EXISTS("S3001", "分类名称已存在"),
    CATEGORY_NOT_FOUND("S3002", "分类不存在"),
    CATEGORY_IN_USE("S3003", "分类正在被使用，无法删除"),

    STORAGE_PROVIDER_NAME_EXISTS("S3101", "存储提供商名称重复"),
    STORAGE_PROVIDER_NOT_FOUND("S3102", "存储提供商不存在"),

    /**
     * 书籍模块相关 B4xxx
     */
    BOOK_NOT_FOUND("B4001", "书籍不存在"),
    BOOK_UPLOAD_FAILED("B4002", "书籍上传失败"),
    BOOK_PARSE_FAILED("B4003", "书籍解析失败"),
    BOOK_FILE_TOO_LARGE("B4004", "书籍文件过大"),
    BOOK_FILE_TYPE_NOT_SUPPORTED("B4005", "不支持的书籍文件类型");


    private final String code;
    private final String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}