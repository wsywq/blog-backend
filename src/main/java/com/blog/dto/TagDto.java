package com.blog.dto;

import java.time.LocalDateTime;

/**
 * 标签DTO
 * 
 * @author blog
 * @since 2024-01-01
 */
public record TagDto(
    Long id,
    String name,
    String color,
    LocalDateTime createTime,
    LocalDateTime updateTime
) {
    public TagDto {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("标签名称不能为空");
        }
    }
}
