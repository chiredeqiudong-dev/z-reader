package com.zreader.exception;

import com.zreader.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import static com.zreader.enums.ResultCode.ERROR;
import static com.zreader.enums.ResultCode.SYSTEM_ERROR;


/**
 * 全局异常处理器
 * 扫描所有 @RestController 标记的类
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 捕获自定义的业务异常 (BizException)
     */
    @ExceptionHandler(BizException.class)
    public ApiResponse<Void> handleBizException(BizException e) {
        log.warn("业务异常 (BizException): {}", e.getMessage());
        return ApiResponse.error(e.getResultCode().getCode(), e.getResultCode().getMsg());
    }

    /**
     * 捕获参数校验异常 (如 DTO 上的 @NotBlank, @NotNull)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Void> handleValidationException(MethodArgumentNotValidException e) {
        // 获取所有错误列表
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        // 拼接错误信息用 ", " 拼接
        String message = allErrors.stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.warn("参数校验异常 (ValidationException): {}", message);
        return ApiResponse.error(ERROR.getCode(), message);
    }

    /**
     * 捕获所有其他未处理的异常
     * 如 NullPointerException, SQLException 等
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleException(Exception e) {
        log.error("系统未知异常 (Exception):", e);
        return ApiResponse.error(SYSTEM_ERROR.getCode(), SYSTEM_ERROR.getMsg());
    }
}