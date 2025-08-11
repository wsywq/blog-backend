package com.blog.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

/**
 * 创建文章请求DTO
 * 
 * @author blog
 * @since 2024-01-01
 */
public record CreateArticleRequest(
    @NotBlank(message = "文章标题不能为空")
    String title,
    
    @NotBlank(message = "文章内容不能为空")
    String content,
    
    @NotBlank(message = "文章摘要不能为空")
    String summary,
    
    @NotBlank(message = "作者不能为空")
    String author,
    
    Long categoryId,
    Set<Long> tagIds,
    String coverImage
) {}
