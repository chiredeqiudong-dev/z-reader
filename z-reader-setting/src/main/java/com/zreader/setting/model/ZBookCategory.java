package com.zreader.setting.model;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 书籍分类实体类
 *
 * @author zy
 * @date 2025/11/12
 */
@TableName("z_book_category")
public class ZBookCategory implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 分类唯一ID
     * 数据库: id INT UNSIGNED PRIMARY KEY
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 分类名称
     * 数据库: category_name VARCHAR(64) NOT NULL UNIQUE
     */
    private String categoryName;

    /**
     * 该分类下书籍数量
     * 数据库: book_count INT UNSIGNED NOT NULL DEFAULT 0
     */
    private Integer bookCount;

    /**
     * 创建时间
     * 数据库: created_at DATETIME(6) NOT NULL DEFAULT ...
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     * 数据库: updated_at DATETIME(6) NOT NULL DEFAULT ... ON UPDATE ...
     */
    private LocalDateTime updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getBookCount() {
        return bookCount;
    }

    public void setBookCount(Integer bookCount) {
        this.bookCount = bookCount;
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

