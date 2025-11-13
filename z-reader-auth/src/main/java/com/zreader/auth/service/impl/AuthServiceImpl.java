package com.zreader.auth.service.impl;


import cn.dev33.satoken.stp.StpUtil;
import com.zreader.auth.mapper.ZUserMapper;
import com.zreader.auth.model.*;
import com.zreader.auth.service.AuthService;
import com.zreader.common.exception.BizException;
import com.zreader.common.response.ApiResponse;
import com.zreader.common.utils.HexUtil;
import org.springframework.stereotype.Service;

import static com.zreader.common.constant.AuthConstant.ROLE_GUEST;
import static com.zreader.common.enums.ResultCode.*;

/**
 * 认证服务实现类
 *
 * @author zy
 * @date 2025/11/9
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final ZUserMapper zUserMapper;

    public AuthServiceImpl(ZUserMapper zUserMapper) {
        this.zUserMapper = zUserMapper;
    }

    @Override
    public ApiResponse<LoginVO> login(LoginDTO loginDto) {
        // 根据用户名查询用户
        ZUser user = zUserMapper.selectByUsername(loginDto.getUsername());
        if (user == null) {
            return ApiResponse.error(AUTH_LOGIN_FAILED.getCode(), AUTH_LOGIN_FAILED.getMsg());
        }

        // 验证密码
        if (!HexUtil.verify(user.getPassword(), loginDto.getPassword())) {
            return ApiResponse.error(AUTH_LOGIN_FAILED.getCode(), AUTH_LOGIN_FAILED.getMsg());
        }

        StpUtil.login(user.getId());
        String token = StpUtil.getTokenValue();
        UserInfoVO userInfo = convertToUserInfoVO(user);
        return ApiResponse.ok(new LoginVO(token, userInfo));
    }

    @Override
    public ApiResponse<Void> registerGuest(RegisterDTO registerDto) {
        // 检查用户名是否已存在
        ZUser zUser = zUserMapper.selectByUsername(registerDto.getUsername());
        if (zUser != null) {
            return ApiResponse.error(AUTH_USERNAME_EXISTS.getCode(), AUTH_USERNAME_EXISTS.getMsg());
        }

        // 创建新用户
        ZUser user = new ZUser();
        user.setUsername(registerDto.getUsername());
        user.setPassword(HexUtil.encryptAndFormat(registerDto.getPassword()));
        // 设置为游客角色
        user.setRole(ROLE_GUEST);

        // 保存
        int result = zUserMapper.insert(user);
        if (result <= 0) {
            return ApiResponse.error();
        }

        return ApiResponse.ok();
    }

    @Override
    public ApiResponse<UserInfoVO> getCurrentUser() {
        // 从 Sa-Token 获取当前登录用户ID
        Object loginId = StpUtil.getLoginIdDefaultNull();
        if (loginId == null) {
            return ApiResponse.error(AUTH_NOT_LOGIN.getCode(), AUTH_NOT_LOGIN.getMsg());
        }

        ZUser user = zUserMapper.selectById((String) loginId);
        if (user == null) {
            throw new BizException(USER_NOT_FOUND);
        }

        UserInfoVO userInfoVO = convertToUserInfoVO(user);
        return ApiResponse.ok(userInfoVO);
    }

    @Override
    public void logout() {
        // Sa-Token 登出
        StpUtil.logout();
    }

    @Override
    public ApiResponse<UserInfoVO> updateCurrentUser(UpdateUserInfoDTO updateUserInfoDto) {
        // 从 Sa-Token 获取当前登录用户ID
        Object loginId = StpUtil.getLoginIdDefaultNull();
        if (loginId == null) {
            return ApiResponse.error(AUTH_NOT_LOGIN.getCode(), AUTH_NOT_LOGIN.getMsg());
        }

        Integer userId = Integer.parseInt((String) loginId);

        // 检查用户是否存在
        ZUser user = zUserMapper.selectById(userId);
        if (user == null) {
            return ApiResponse.error(USER_NOT_FOUND.getCode(), USER_NOT_FOUND.getMsg());
        }

        int result = zUserMapper.updateUserInfo(userId, updateUserInfoDto);
        if (result <= 0) {
            return ApiResponse.error();
        }

        // 重新查询更新后的用户信息
        user = zUserMapper.selectById(userId);
        UserInfoVO userInfoVO = convertToUserInfoVO(user);
        return ApiResponse.ok(userInfoVO);
    }

    /**
     * 将用户实体转换为用户信息VO
     *
     * @param user 用户实体
     * @return 用户信息VO
     */
    private UserInfoVO convertToUserInfoVO(ZUser user) {
        UserInfoVO userInfoVO = new UserInfoVO();

        userInfoVO.setId(user.getId());
        userInfoVO.setUsername(user.getUsername());
        userInfoVO.setNickname(user.getNickname());
        userInfoVO.setEmail(user.getEmail());
        userInfoVO.setRole(user.getRole());
        userInfoVO.setBio(user.getBio());
        userInfoVO.setAvatarUrl(user.getAvatarUrl());
        userInfoVO.setGender(user.getGender());
        userInfoVO.setCreatedAt(user.getCreatedAt());
        userInfoVO.setUpdatedAt(user.getUpdatedAt());
        return userInfoVO;
    }
}

