package com.zreader.book.service;


import com.zreader.book.model.UploadRecord;
import com.zreader.common.response.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 书籍上传服务
 *
 * @author zy
 * @date 2025/11/15
 */
public interface BookUploadService {


    /**
     * 上传书籍文件（小文件直传）
     *
     * @param file              上传的文件
     * @param userId            用户ID
     * @param storageProviderId 存储服务ID
     * @return 上传结果（包含 uploadId）
     */
    ApiResponse<UploadRecord> uploadBook(MultipartFile file, Integer userId, Integer storageProviderId);


    /**
     * 获取上传状态
     *
     * @param uploadId 上传ID
     * @return 上传状态
     */
    ApiResponse<UploadRecord> getUploadStatus(String uploadId);
}
