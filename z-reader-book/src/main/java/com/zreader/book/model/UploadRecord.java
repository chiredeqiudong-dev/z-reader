package com.zreader.book.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 上传记录（用于内存追踪上传进度）
 *
 * @author zy
 * @date 2025/11/13
 */
public class UploadRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 上传ID（UUID）
     */
    private String uploadId;

    /**
     * 书籍ID
     */
    private Integer bookId;

    /**
     * 原始文件名
     */
    private String originalFilename;

    /**
     * 上传状态（PENDING/UPLOADING/PARSING/COMPLETED/FAILED）
     */
    private String status;

    /**
     * 进度百分比（0-100）
     */
    private Integer progress;

    /**
     * 错误信息（失败时记录）
     */
    private String errorMessage;

    // Getters and Setters

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
}

