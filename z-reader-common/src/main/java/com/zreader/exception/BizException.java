package com.zreader.exception;

import com.zreader.enums.ResultCode;

/**
 * 自定义业务异常 (Business Exception)
 */
public class BizException extends RuntimeException {

    /**
     * 异常对应的状态码
     */
    private final ResultCode resultCode;

    public BizException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.resultCode = resultCode;
    }

    public BizException(ResultCode resultCode, String msg) {
        super(msg);
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}