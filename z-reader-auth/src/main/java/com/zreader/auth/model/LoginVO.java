package com.zreader.auth.model;


/**
 * 登录响应视图对象
 *
 * @author zy
 * @date 2025/11/10
 */
public class LoginVO {

    /**
     * 访问令牌
     */
    private String token;

    /**
     * 用户信息
     */
    private UserInfoVO userInfo;

    public LoginVO() {
    }

    public LoginVO(String token, UserInfoVO userInfo) {
        this.token = token;
        this.userInfo = userInfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfoVO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoVO userInfo) {
        this.userInfo = userInfo;
    }
}

