package com.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.blog.enums.ArticleStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文章实体类
 * 
 * @author blog
 * @since 2024-01-01
 */
@TableName("articles")
@Data
@EqualsAndHashCode(callSuper = true)
public class Article extends BaseEntity {
    
    @NotBlank(message = "文章标题不能为空")
    @TableField("title")
    private String title;
    
    @NotBlank(message = "文章内容不能为空")
    @TableField("content")
    private String content;
    
    @NotBlank(message = "文章摘要不能为空")
    @TableField("summary")
    private String summary;
    
    @NotBlank(message = "作者不能为空")
    @TableField("author")
    private String author;
    
    @TableField("status")
    private ArticleStatus status = ArticleStatus.DRAFT;
    
    @TableField("category_id")
    private Long categoryId;
    
    @TableField("cover_image")
    private String coverImage;
    
    @TableField("view_count")
    private Long viewCount = 0L;
}
