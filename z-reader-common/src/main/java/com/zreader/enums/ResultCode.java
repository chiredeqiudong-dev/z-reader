package com.zreader.enums;


/**
 * 统一响应状态码枚举
 */
public enum ResultCode {

    /**
     * 通用
     */
    SUCCESS("2000", "success"),
    ERROR("5000", "error"),
    SYSTEM_ERROR("S1000","系统未知错误");


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