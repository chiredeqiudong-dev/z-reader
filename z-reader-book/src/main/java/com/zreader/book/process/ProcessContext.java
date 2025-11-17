package com.zreader.book.process;


import com.zreader.book.model.UploadRecord;
import com.zreader.book.model.ZBookStorage;
import com.zreader.common.response.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传上下文参数
 *
 * @author zy
 * @date 2025/11/15
 */
public class ProcessContext {

    /**
     * 上传ID（文件上传标识）
     */
    private String uploadId;

    /**
     * 用户Id
     */
    private Integer userId;

    /**
     * 文件
     */
    private MultipartFile file;

    /**
     * 书籍存储对象
     */
    private ZBookStorage bookStorage;

    /**
     * 责任链中断标识
     * false 中断
     * true 执行
     */
    private boolean next;

    /**
     * 执行结果
     */
    private ApiResponse<UploadRecord> response;

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public ZBookStorage getBookStorage() {
        return bookStorage;
    }

    public void setBookStorage(ZBookStorage bookStorage) {
        this.bookStorage = bookStorage;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public ApiResponse<UploadRecord> getResponse() {
        return response;
    }

    public void setResponse(ApiResponse<UploadRecord> response) {
        this.response = response;
    }
}
