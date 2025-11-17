package com.zreader.book.process.action;

import com.zreader.book.model.UploadRecord;
import com.zreader.book.model.ZBookStorage;
import com.zreader.book.process.ProcessContext;
import com.zreader.book.process.ProcessControl;
import com.zreader.book.service.UploadRecordService;
import com.zreader.common.constant.BookConstant;
import com.zreader.common.enums.BookType;
import com.zreader.common.enums.ResultCode;
import com.zreader.common.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文件类型和大小限制 Action
 *
 * @author zy
 * @date 2025/11/16
 */
@Service
public class FileTypeAndSizeLimitAction implements ProcessControl {

    private static final Logger log = LoggerFactory.getLogger(FileTypeAndSizeLimitAction.class);
    private final List<String> fileTypes = BookType.getBookTypes();

    private final UploadRecordService uploadRecordService;

    public FileTypeAndSizeLimitAction(UploadRecordService uploadRecordService) {
        this.uploadRecordService = uploadRecordService;
    }

    @Override
    public void process(ProcessContext context) {
        ZBookStorage bookStorage = context.getBookStorage();
        String fileExt = bookStorage.getFileExt();
        long fileSize = context.getBookStorage().getFileSize();

        // 检查文件类型
        boolean isValidType = false;
        for (String fileType : fileTypes) {
            if (!fileType.equals(fileExt)) {
                isValidType = true;
                break;
            }
        }
        if (!isValidType) {
            log.warn("不支持的文件类型: filename={}, extension={}", bookStorage.getOriginalFilename(), fileExt);
            // 更新上传记录
            UploadRecord uploadRecord = uploadRecordService.updateError(context.getUploadId(), ResultCode.BOOK_FILE_TYPE_NOT_SUPPORTED.getMsg());
            context.setNext(false);
            context.setResponse(ApiResponse.error(ResultCode.BOOK_FILE_TYPE_NOT_SUPPORTED, uploadRecord));
            return;
        }

        // 检查文件大小
        if (fileSize > BookConstant.MAX_FILE_SIZE) {
            log.warn("文件过大: filename={}, size={} MB, max={} MB", bookStorage.getOriginalFilename(), fileSize / 1024 / 1024, BookConstant.MAX_FILE_SIZE / 1024 / 1024);
            UploadRecord uploadRecord = uploadRecordService.updateError(context.getUploadId(),
                    ResultCode.BOOK_FILE_TOO_LARGE.getMsg());
            context.setResponse(ApiResponse.error(ResultCode.BOOK_FILE_TOO_LARGE, uploadRecord));
            context.setNext(false);
            return;
        }
        uploadRecordService.updateStatus(context.getUploadId(), BookConstant.UPLOAD_STATUS_PENDING, 5);
        context.setNext(true);
    }
}

