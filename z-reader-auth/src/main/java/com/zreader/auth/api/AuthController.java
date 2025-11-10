package com.zreader.auth.api;


import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import com.zreader.auth.model.LoginDTO;
import com.zreader.auth.model.LoginVO;
import com.zreader.auth.model.RegisterDTO;
import com.zreader.auth.model.UserInfoVO;
import com.zreader.auth.service.AuthService;
import com.zreader.response.ApiResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.zreader.auth.constant.AuthConstant.ROLE_ADMIN_STR;

/**
 * 认证控制器
 * 提供登录、注册游客、获取用户信息、登出等接口
 *
 * @author zy
 * @date 2025/11/9
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * 用户登录（管理员或游客）
     *
     * @param loginDto 包含用户名和密码
     * @return 包含 Token 和用户信息的响应
     */
    @PostMapping("/login")
    public ApiResponse<LoginVO> login(@RequestBody @Validated LoginDTO loginDto) {
        return authService.login(loginDto);
    }

    /**
     * 管理员注册游客
     * 需要管理员权限
     *
     * @param registerDto 注册信息
     * @return 新注册的用户信息
     */
    @PostMapping("/register-guest")
    @SaCheckRole(value = ROLE_ADMIN_STR)
    public ApiResponse<Void> registerGuest(@RequestBody @Validated RegisterDTO registerDto) {
        return authService.registerGuest(registerDto);
    }

    /**
     * 获取当前登录用户信息
     *
     * @return 当前用户信息
     */
    @GetMapping("/current-user")
    public ApiResponse<UserInfoVO> getCurrentUser() {
        return authService.getCurrentUser();
    }

    /**
     * 用户登出
     *
     * @return 成功响应
     */
    @PostMapping("/logout")
    public ApiResponse<Void> logout() {
        authService.logout();
        return ApiResponse.ok();
    }

    /**
     * 检查登录状态
     *
     * @return 是否已登录
     */
    @GetMapping("/check-login")
    public ApiResponse<Boolean> checkLogin() {
        return ApiResponse.ok(StpUtil.isLogin());
    }
}
