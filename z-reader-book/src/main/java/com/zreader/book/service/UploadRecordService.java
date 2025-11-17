package com.zreader.book.service;

import com.zreader.book.model.UploadRecord;
import com.zreader.book.websocket.UploadProgressWebSocketHandler;
import com.zreader.common.constant.BookConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 上传记录服务（内存管理）
 *
 * @author zy
 * @date 2025/11/13
 */
@Service
public class UploadRecordService {

    /**
     * uploadId -> UploadRecord
     */
    private final Map<String, UploadRecord> uploadRecords = new ConcurrentHashMap<>();

    @Autowired(required = false)
    private UploadProgressWebSocketHandler webSocketHandler;

    /**
     * 创建上传记录
     *
     * @param uploadId 书籍上传唯一标识
     * @param originalFilename 原始文件名
     */
    public UploadRecord creatRecord(String uploadId,String originalFilename) {
        UploadRecord record = new UploadRecord();
        record.setUploadId(uploadId);
        record.setOriginalFilename(originalFilename);
        record.setStatus(BookConstant.UPLOAD_STATUS_PENDING);
        record.setProgress(10);
        // 记录保存
        uploadRecords.put(record.getUploadId(), record);
        return  record;
    }

    /**
     * 获取上传记录
     *
     * @param uploadId 上传ID
     * @return 上传记录
     */
    public UploadRecord getRecord(String uploadId) {
        return uploadRecords.get(uploadId);
    }

    /**
     * 更新上传状态
     *
     * @param uploadId 上传ID
     * @param status   状态
     * @param progress 进度
     */
    public void updateStatus(String uploadId, String status, Integer progress) {
        UploadRecord record = uploadRecords.get(uploadId);
        if (record != null) {
            record.setStatus(status);
            record.setProgress(progress);

            // 推送 WebSocket 更新
            if (webSocketHandler != null) {
                webSocketHandler.pushProgressUpdate(uploadId);
            }
        }
    }

    /**
     * 更新失败信息
     *
     * @param uploadId     上传ID
     * @param errorMessage 错误信息
     */
    public UploadRecord updateError(String uploadId, String errorMessage) {
        UploadRecord record = uploadRecords.get(uploadId);
        if (record != null) {
            record.setStatus("FAILED");
            record.setErrorMessage(errorMessage);
            // 推送 WebSocket 更新
            if (webSocketHandler != null) {
                webSocketHandler.pushProgressUpdate(uploadId);
            }
        }
        return record;
    }

    /**
     * 更新完成信息
     *
     * @param uploadId 上传ID
     * @param bookId   书籍ID
     */
    public UploadRecord updateCompleted(String uploadId, Integer bookId) {
        UploadRecord record = uploadRecords.get(uploadId);
        if (record != null) {
            record.setStatus(BookConstant.UPLOAD_STATUS_COMPLETED);
            record.setProgress(100);
            record.setBookId(bookId);

            // 推送 WebSocket 更新
            if (webSocketHandler != null) {
                webSocketHandler.pushProgressUpdate(uploadId);
            }
        }
        return record;
    }

    /**
     * 删除上传记录
     *
     * @param uploadId 上传ID
     */
    public void removeRecord(String uploadId) {
        uploadRecords.remove(uploadId);
    }
}

