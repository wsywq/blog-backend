package com.blog.dto;

import java.time.LocalDateTime;

/**
 * 分类DTO
 * 
 * @author blog
 * @since 2024-01-01
 */
public record CategoryDto(
    Long id,
    String name,
    String description,
    String icon,
    LocalDateTime createTime,
    LocalDateTime updateTime
) {
    public CategoryDto {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("分类名称不能为空");
        }
    }
}
