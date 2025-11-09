package com.zreader.app;


import cn.dev33.satoken.SaManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zy
 * @date 2025/11/5
 */
@SpringBootApplication(scanBasePackages = {"com.zreader"})
@MapperScan("com.zreader.*.mapper")
public class ZReaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZReaderApplication.class, args);
        System.out.println("启动成功，Sa-Token 配置如下：" + SaManager.getConfig());
    }
}
