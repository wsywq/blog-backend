package com.blog.dto;

import com.blog.enums.CommentStatus;
import java.time.LocalDateTime;

/**
 * 评论数据传输对象
 */
public record CommentDto(
    Long id,
    Long articleId,
    Long userId,
    String content,
    CommentStatus status,
    LocalDateTime createTime,
    LocalDateTime updateTime
) {
    public CommentDto {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("评论内容不能为空");
        }
    }
}

