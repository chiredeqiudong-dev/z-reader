package com.zreader.auth.service;


import com.zreader.auth.model.*;
import com.zreader.common.response.ApiResponse;

/**
 * 认证服务接口
 *
 * @author zy
 * @date 2025/11/9
 */
public interface AuthService {

    /**
     * 用户登录（管理员或游客）
     *
     * @param loginDto 登录数据
     * @return 登录响应（包含token和用户信息）
     */
    ApiResponse<LoginVO> login(LoginDTO loginDto);

    /**
     * 管理员注册游客
     * 仅管理员有权限调用
     *
     * @param registerDto 注册数据
     * @return 新注册的用户信息
     */
    ApiResponse<Void> registerGuest(RegisterDTO registerDto);

    /**
     * 获取当前登录用户信息
     *
     * @return 用户信息
     */
    ApiResponse<UserInfoVO> getCurrentUser();

    /**
     * 用户登出
     */
    void logout();

    /**
     * 更新当前用户信息
     *
     * @param updateUserInfoDto 用户信息更新数据
     * @return 更新后的用户信息
     */
    ApiResponse<UserInfoVO> updateCurrentUser(UpdateUserInfoDTO updateUserInfoDto);
}
