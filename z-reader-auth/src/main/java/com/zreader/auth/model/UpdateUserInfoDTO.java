package com.zreader.auth.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

/**
 * 用户信息更新请求 DTO
 *
 * @author zy
 * @date 2025/11/13
 */
public class UpdateUserInfoDTO {

    /**
     * 昵称（可选）
     */
    @Size(min = 1, max = 20, message = "昵称长度必须在1-20个字符之间")
    private String nickname;

    /**
     * 邮箱（可选）
     */
    @Email(message = "邮箱格式不正确")
    @Size(max = 255, message = "邮箱长度不能超过255个字符")
    private String email;

    /**
     * 头像URL（可选）
     */
    @Size(max = 512, message = "头像URL长度不能超过512个字符")
    private String avatarUrl;

    /**
     * 性别（可选，0:女, 1:男）
     */
    @Min(value = 0, message = "性别只能是0或1")
    @Max(value = 1, message = "性别只能是0或1")
    private Integer gender;

    /**
     * 个人简介（可选）
     */
    @Size(max = 64, message = "个人简介长度不能超过64个字符")
    private String bio;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}

