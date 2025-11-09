package com.zreader.auth.domain;


import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户实体类
 *
 * @author zy
 * @date 2025/11/9
 */
@TableName("z_user")
public class ZUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一ID
     * 数据库: id TINYINT PRIMARY KEY
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户账号
     * 数据库: username VARCHAR(64) NOT NULL UNIQUE
     */
    private String username;

    /**
     * 昵称
     * 数据库: nickname VARCHAR(20) DEFAULT 'z-reader'
     */
    private String nickname;

    /**
     * 哈希后的密码
     * 数据库: z_password VARCHAR(255) NOT NULL
     */
    @TableField("z_password")
    private String password;

    /**
     * 邮箱
     * 数据库: email VARCHAR(255) DEFAULT NULL
     */
    private String email;

    /**
     * 头像URL
     * 数据库: avatar_url VARCHAR(512) DEFAULT NULL
     */
    private String avatarUrl;

    /**
     * 性别
     * 数据库: gender TINYINT DEFAULT NULL (0:女, 1:男)
     */
    private Integer gender;

    /**
     * 个人简介
     * 数据库: bio VARCHAR(255) DEFAULT NULL
     */
    private String bio;

    /**
     * 用户角色
     * 数据库: role TINYINT NOT NULL (0=ROOT_USER 1=COMMON_USER)
     */
    private Integer role;

    /**
     * 创建时间
     * 数据库: created_at DATETIME(6) NOT NULL DEFAULT ...
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 更新时间
     * 数据库: updated_at DATETIME(6) NOT NULL DEFAULT ... ON UPDATE ...
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}