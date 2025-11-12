package com.zreader.common.exception;


import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.zreader.common.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.zreader.common.enums.ResultCode.*;


/**
 * Sa-Token 异常处理器
 * 用于处理认证和授权相关的异常
 *
 * @author zy
 * @date 2025/11/10
 */
@RestControllerAdvice
@Order(1)  // 优先级高于全局异常处理器
public class SaTokenExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(SaTokenExceptionHandler.class);

    /**
     * 处理未登录异常
     */
    @ExceptionHandler(NotLoginException.class)
    public ApiResponse<Void> handleNotLoginException(NotLoginException e) {
        log.warn("未登录异常: {}", e.getMessage());

        // 根据不同的未登录类型返回不同的提示
        String message = switch (e.getType()) {
            case NotLoginException.NOT_TOKEN -> "未提供Token";
            case NotLoginException.INVALID_TOKEN -> "Token无效";
            case NotLoginException.TOKEN_TIMEOUT -> "Token已过期";
            case NotLoginException.BE_REPLACED -> "Token已被顶下线";
            case NotLoginException.KICK_OUT -> "Token已被踢下线";
            default -> "未登录";
        };

        return ApiResponse.error(AUTH_NOT_LOGIN.getCode(), message);
    }

    /**
     * 处理无角色异常
     */
    @ExceptionHandler(NotRoleException.class)
    public ApiResponse<Void> handleNotRoleException(NotRoleException e) {
        log.warn("无角色权限异常: 需要角色 {}", e.getRole());
        return ApiResponse.error(AUTH_ONLY_ADMIN.getCode(), AUTH_ONLY_ADMIN.getMsg());
    }

    /**
     * 处理无权限异常
     */
    @ExceptionHandler(NotPermissionException.class)
    public ApiResponse<Void> handleNotPermissionException(NotPermissionException e) {
        log.warn("无权限异常: 需要权限 {}", e.getPermission());
        return ApiResponse.error(AUTH_NO_PERMISSION.getCode(), AUTH_NO_PERMISSION.getMsg());
    }
}

