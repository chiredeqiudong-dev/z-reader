package com.zreader.common.response;


import com.zreader.common.enums.ResultCode;

import static com.zreader.common.enums.ResultCode.ERROR;
import static com.zreader.common.enums.ResultCode.SUCCESS;

/**
 * 统一返回格式
 *
 * @author zy
 * @date 2025/11/8
 */
public class ApiResponse<T> {

    private String code;
    private String msg;
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功
     */
    public static <T> ApiResponse<T> ok() {
        return new ApiResponse<>(SUCCESS.getCode(), SUCCESS.getMsg(), null);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(SUCCESS.getCode(), SUCCESS.getMsg(), data);
    }

    public static <T> ApiResponse<T> ok(String code,String msg) {
        return new ApiResponse<>(code, msg, null);
    }

    public static <T> ApiResponse<T> ok(String code, String msg, T data) {
        return new ApiResponse<>(code, msg, data);
    }

    /**
     * 失败
     */
    public static <T> ApiResponse<T> error() {
        return new ApiResponse<>(ERROR.getCode(), ERROR.getMsg(), null);
    }

    public static <T> ApiResponse<T> error(String msg) {
        return new ApiResponse<>(ERROR.getCode(), msg, null);
    }

    public static <T> ApiResponse<T> error(String code,String msg) {
        return new ApiResponse<>(code, msg, null);
    }

    public static <T> ApiResponse<T> error(String code, String msg, T data) {
        return new ApiResponse<>(code, msg, data);
    }

    public static <T> ApiResponse<T> error(ResultCode resultCode,T data) {
        return new ApiResponse<>(resultCode.getCode(), resultCode.getMsg(), data);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
