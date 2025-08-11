package com.blog.dto;

import com.blog.enums.UserRole;
import java.time.LocalDateTime;

/**
 * 用户数据传输对象
 */
public record UserDto(
    Long id,
    String username,
    String email,
    String avatar,
    String githubId,
    UserRole role,
    LocalDateTime createTime,
    LocalDateTime updateTime
) {
    public UserDto {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("邮箱不能为空");
        }
    }
}

