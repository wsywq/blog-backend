package com.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 个人博客后端应用主类
 * 
 * @author blog
 * @since 2024-01-01
 */
@SpringBootApplication
@MapperScan("com.blog.mapper")
@EnableAsync
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }
}
