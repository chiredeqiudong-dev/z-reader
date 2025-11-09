package com.zreader.auth.service;


import com.zreader.auth.dto.LoginDTO;

/**
 * 用户登录校验
 *
 * @author zy
 * @date 2025/11/9
 */
public interface AuthService {

    /**
     * 登录
     * @param loginDto 登录 DTO
     * @return 登录成功后生成的 Token
     */
    String login(LoginDTO loginDto);
}
