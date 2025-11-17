package com.zreader.book.process.action;

import com.zreader.book.model.UploadRecord;
import com.zreader.book.model.ZBookStorage;
import com.zreader.book.process.ProcessContext;
import com.zreader.book.process.ProcessControl;
import com.zreader.book.service.UploadRecordService;
import com.zreader.common.constant.BookConstant;
import com.zreader.common.enums.ResultCode;
import com.zreader.common.exception.BizException;
import com.zreader.common.response.ApiResponse;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBufferedFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class FileDRMCheckAction implements ProcessControl {

    private static final Logger log = LoggerFactory.getLogger(FileDRMCheckAction.class);
    private final UploadRecordService uploadRecordService;

    public FileDRMCheckAction(UploadRecordService uploadRecordService) {
        this.uploadRecordService = uploadRecordService;
    }


    @Override
    public void process(ProcessContext context) {
        MultipartFile file = context.getFile();
        ZBookStorage bookStorage = context.getBookStorage();

        // 判断 PDF/EPUB 等是否存在 DRM
        boolean hasDrm = false;
        try (InputStream inputStream = file.getInputStream()) {
            hasDrm = switch (bookStorage.getFileExt()) {
                case "epub" -> checkEpubDrm(inputStream);
                case "pdf" -> checkPdfDrm(inputStream);
                case "azw", "azw3" -> {
                    // .azw 和 .azw3 经常有 DRM，但没有 100% 可靠的简单检查方法
                    log.warn("File {} is .azw/.azw3. DRM status cannot be reliably determined without deeper inspection. Passing.", bookStorage.getOriginalFilename());
                    yield true;
                }
                default -> {
                    log.info("No DRM check implemented for .{} files. Passing.", bookStorage.getFileExt());
                    yield true;
                }
            };
        } catch (Exception e) {
            log.error("FileDRMCheckAction: Unexpected error during DRM check for {}.", bookStorage.getOriginalFilename(), e);
            // 更新上传记录
            uploadRecordService.updateError(context.getUploadId(), ResultCode.BOOK_UPLOAD_FAILED.getMsg());
            throw new BizException(ResultCode.BOOK_UPLOAD_FAILED, e.getMessage());
        }

        // 根据检查结果设置上下文
        if (hasDrm) {
            context.setNext(false);
            UploadRecord uploadRecord = uploadRecordService.updateError(context.getUploadId(), "Upload failed: File is protected by DRM.");
            context.setResponse(ApiResponse.error(ResultCode.BOOK_UPLOAD_FAILED, uploadRecord));
        } else {
            uploadRecordService.updateStatus(context.getUploadId(), BookConstant.UPLOAD_STATUS_PENDING, 20);
            context.setNext(true);
        }
    }

    /**
     * 检查 EPUB 文件的 DRM。
     * EPUB 是 ZIP 压缩文件。如果 META-INF/encryption.xml 存在，则表示有 DRM。
     *
     * @return 是否存在 DRM
     */
    private boolean checkEpubDrm(InputStream inputStream) throws IOException {
        // ZipInputStream 会在关闭时自动关闭底层的 inputStream
        try (ZipInputStream zipStream = new ZipInputStream(inputStream)) {
            ZipEntry entry;
            while ((entry = zipStream.getNextEntry()) != null) {
                // 不区分大小写并使用正斜杠
                if (entry.getName().equalsIgnoreCase("META-INF/encryption.xml")) {
                    return true;
                }
                zipStream.closeEntry();
            }
        }
        return false;
    }

    /**
     * 检查 PDF 文件的 DRM（加密）
     *
     * @return 是否存在 DRM
     */
    private boolean checkPdfDrm(InputStream inputStream) throws IOException {
        Path tempPdf = null;
        try {
            // 创建一个临时文件
            tempPdf = Files.createTempFile("drmcheck_" + UUID.randomUUID(), ".pdf");
            // 将 MultipartFile 的内容复制到临时文件
            Files.copy(inputStream, tempPdf, StandardCopyOption.REPLACE_EXISTING);
            // 使用 Loader 和 RandomAccessReadBufferedFile 来加载临时文件
            try (PDDocument document = Loader.loadPDF(new RandomAccessReadBufferedFile(tempPdf.toFile()))) {
                return document.getEncryption() != null;
            }
        } finally {
            // 确保临时文件总被删除
            if (tempPdf != null) {
                try {
                    Files.deleteIfExists(tempPdf);
                } catch (IOException e) {
                    log.warn("Failed to delete temp file: {}", tempPdf, e);
                }
            }
        }
    }

}