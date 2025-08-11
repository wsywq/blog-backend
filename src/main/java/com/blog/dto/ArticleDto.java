package com.blog.dto;

import com.blog.enums.ArticleStatus;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * 文章DTO
 * 
 * @author blog
 * @since 2024-01-01
 */
public record ArticleDto(
    Long id,
    String title,
    String content,
    String summary,
    String author,
    ArticleStatus status,
    CategoryDto category,
    Set<TagDto> tags,
    String coverImage,
    Long viewCount,
    LocalDateTime createTime,
    LocalDateTime updateTime
) {
    public ArticleDto {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("标题不能为空");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("内容不能为空");
        }
        if (summary == null || summary.isBlank()) {
            throw new IllegalArgumentException("摘要不能为空");
        }
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("作者不能为空");
        }
    }
}
