package com.zreader.book.process.action;


import com.zreader.book.mapper.ZBookStorageMapper;
import com.zreader.book.model.UploadRecord;
import com.zreader.book.model.ZBookStorage;
import com.zreader.book.process.ProcessContext;
import com.zreader.book.process.ProcessControl;
import com.zreader.book.service.UploadRecordService;
import com.zreader.common.constant.BookConstant;
import com.zreader.common.enums.ResultCode;
import com.zreader.common.exception.BizException;
import com.zreader.common.response.ApiResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * 文件秒传
 *
 * @author zy
 * @date 2025/11/15
 */
@Service
public class SecondsUploadAction implements ProcessControl {

    private static final Logger log = LoggerFactory.getLogger(SecondsUploadAction.class);

    private final ZBookStorageMapper zBookStorageMapper;
    private final UploadRecordService uploadRecordService;

    public SecondsUploadAction(ZBookStorageMapper zBookStorageMapper, UploadRecordService uploadRecordService) {
        this.zBookStorageMapper = zBookStorageMapper;
        this.uploadRecordService = uploadRecordService;
    }

    @Override
    public void process(ProcessContext context) {
        // 文件 hash
        String fileHash = calculateFileHash(context.getFile());
        context.getBookStorage().setFileHash(fileHash);
        ZBookStorage storage = zBookStorageMapper.selectByFileHash(fileHash);

        // 已存在相同文件
        if (storage != null) {
            log.info("秒传命中,bookId={}; fileHash={}", fileHash, storage.getBookId());

            // 上传完成
            UploadRecord uploadRecord = uploadRecordService.updateCompleted(context.getUploadId(), storage.getBookId());
            context.setResponse(ApiResponse.ok(uploadRecord));
            context.setNext(false);
        } else {
            uploadRecordService.updateStatus(context.getUploadId(), BookConstant.UPLOAD_STATUS_PENDING, 10);
            context.setNext(true);
        }
    }

    /**
     * 流失处理计算文件的 SHA-256 哈希。
     */
    private String calculateFileHash(MultipartFile file) {
        try (InputStream in = file.getInputStream()) {
            return DigestUtils.sha256Hex(in);
        } catch (IOException e) {
            throw new BizException(ResultCode.SYSTEM_ERROR, "计算文件哈希失败" + e.getMessage());
        }
    }

}
