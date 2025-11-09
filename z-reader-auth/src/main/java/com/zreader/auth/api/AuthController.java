package com.zreader.auth.api;


import com.zreader.auth.dto.LoginDTO;
import com.zreader.auth.service.AuthService;
import com.zreader.response.ApiResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
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
     * 馆长登录
     * @param loginDto 包含用户名和密码
     * @return 包含 Token 的 R 响应
     */
    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody @Validated LoginDTO loginDto) {
        String token = authService.login(loginDto);
        return ApiResponse.ok(token);
    }
}
