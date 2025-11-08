package com.zreader.app.test;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

/**
 * @author zy
 * @date 2025/11/5
 */
@RestController
@RequestMapping("/web")
public class WebController {

    /**
     * 测试项目是否启动成功
     */
    @GetMapping("/test")
    public String test() {
        return "hello world";
    }


    // 定义一个用于存储上传文件的根目录
    // "./uploads" 表示存储在项目根目录下的 uploads 文件夹中
    private final Path uploadDir = Paths.get("./uploads");

    /**
     * 处理文件上传的方法
     *
     * @param file 从请求中获取的上传文件，"file" 是前端表单中 <input type="file"> 的 name 属性
     * @return 返回一个包含操作结果的 ResponseEntity
     */
    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {

        // 1. 检查上传目录是否存在，如果不存在则创建
        try {
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("无法创建上传目录: " + e.getMessage());
        }


        // 2. 检查文件是否为空
        if (file.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("上传失败，请选择一个文件。");
        }

        // 3. 获取并清理文件名，防止路径遍历攻击
        String originalFileName = Objects.requireNonNull(file.getOriginalFilename());
        String fileName = originalFileName.substring(originalFileName.lastIndexOf("\\") + 1);

        // 4. 构建目标文件路径
        Path destinationFile = this.uploadDir.resolve(Paths.get(fileName))
                .normalize().toAbsolutePath();

        // 5. 将文件复制到目标位置
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("文件上传失败: " + e.getMessage());
        }

        // 6. 返回成功响应
        return ResponseEntity
                .ok("文件上传成功: " + fileName);
    }

    /**
     * 解析在 'uploads' 目录中已存在的文件。
     * 这是一个用于测试的独立接口。
     *
     * @param fileName 要解析的文件名 (例如: "my-book.epub")
     * @return 包含解析结果或错误信息的 ResponseEntity
     */
    @GetMapping("/parse")
    public ResponseEntity<String> parseExistingFile(@RequestParam("filename") String fileName) {

        // 1. 构建文件的完整路径
        Path filePath = this.uploadDir.resolve(Paths.get(fileName))
                .normalize().toAbsolutePath();

        // 2. 检查文件是否存在
        if (!Files.exists(filePath)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND) // 404 错误
                    .body("文件未找到: " + fileName);
        }

        System.out.println("...开始 Tika 解析 (独立测试): " + filePath + "...");

        try {
            // 3. Tika 核心解析代码
            Tika tika = new Tika();
            Metadata metadata = new Metadata();

            // 4. Tika 直接解析文件路径
            FileInputStream fileInputStream = new FileInputStream(filePath.toFile());
            String content = tika.parseToString(fileInputStream, metadata);

            // 5. 提取元数据
            String title = metadata.get("dc:title");
            String author = metadata.get("dc:creator");
            String language = metadata.get("dc:language");

            // 6. 打印到控制台 (用于服务器端日志)
            System.out.println("--- 📖 元数据 (Metadata) ---");
            System.out.println("书名 (Title): " + title);
            System.out.println("作者 (Creator): " + author);
            System.out.println("语言 (Language): " + language);
            System.out.println("...Tika 解析完成 (独立测试)...");

            // 7. 构建一个清晰的响应信息返回给前端
            String responseBody = String.format(
                    "文件解析成功: %s\n" +
                            "--------------------\n" +
                            "书名: %s\n" +
                            "作者: %s\n" +
                            "语言: %s\n" +
                            "内容: %s\n",
                    fileName, title, author, language,content
            );

            return ResponseEntity.ok(responseBody);

        } catch (IOException | TikaException e) {
            // 捕获所有可能的解析异常
            e.printStackTrace(); // 在服务器端打印详细错误
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("文件解析失败: " + e.getMessage());
        }
    }
}


