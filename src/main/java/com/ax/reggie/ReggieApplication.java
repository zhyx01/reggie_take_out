package com.ax.reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * className: ReggieApplication
 * description:
 *
 * @author: axiang
 * date: 2023/5/1 0001 9:42
 */
@SpringBootApplication
@Slf4j
@ServletComponentScan
@EnableTransactionManagement
@EnableCaching // 开启Spring Cache
public class ReggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class, args);
        log.info("项目启动成功...");
    }
}
