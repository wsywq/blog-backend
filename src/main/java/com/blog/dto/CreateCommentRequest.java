package com.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 创建评论请求对象
 */
public record CreateCommentRequest(
    @NotNull(message = "文章ID不能为空")
    Long articleId,
    
    @NotBlank(message = "评论内容不能为空")
    String content
) {}

