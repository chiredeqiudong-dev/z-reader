package com.zreader.book.process;


import com.zreader.book.model.UploadRecord;

/**
 * 流程控制接口
 *
 * @author zy
 * @date 2025/11/13
 */
public interface ProcessControl {

    /**
     * 执行校验
     *
     * @param context 上下文参数
     */
    void process(ProcessContext context);
}

