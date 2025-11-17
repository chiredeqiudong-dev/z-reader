package com.zreader.book.process;

import com.zreader.book.model.UploadRecord;
import com.zreader.book.process.action.CreateUploadTaskAction;
import com.zreader.book.process.action.FileDRMCheckAction;
import com.zreader.book.process.action.FileTypeAndSizeLimitAction;
import com.zreader.book.process.action.SecondsUploadAction;
import com.zreader.common.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 上传流程责任链
 * 负责协调各个 ProcessControl action 的执行顺序
 *
 * @author zy
 * @date 2025/11/16
 */
@Component
public class UploadProcessChain {

    private static final Logger log = LoggerFactory.getLogger(UploadProcessChain.class);

    private final List<ProcessControl> actions;

    /**
     * 构造器注入，定义责任链顺序
     * 顺序：文件类型/大小校验 -> 秒传检测 -> DRM 检测 -> 创建上传任务
     */
    public UploadProcessChain(SecondsUploadAction secondsUpload, FileDRMCheckAction fileDRMCheckAction, FileTypeAndSizeLimitAction fileTypeAndSizeLimitAction, CreateUploadTaskAction createUploadTaskAction) {
        this.actions = new ArrayList<>();
        this.actions.add(fileTypeAndSizeLimitAction);
        this.actions.add(secondsUpload);
        this.actions.add(fileDRMCheckAction);
        this.actions.add(createUploadTaskAction);

        log.info("流程责任链初始化完成，共 {} 个 action", actions.size());
    }

    /**
     * 执行责任链
     *
     * @param context 流程上下文
     */
    public ApiResponse<UploadRecord> execute(ProcessContext context) {
        for (ProcessControl bookProcess : actions) {
            bookProcess.process(context);
            // 责任链中断
            if (!context.isNext()){
               break;
            }
        }
        return context.getResponse();
    }
}

