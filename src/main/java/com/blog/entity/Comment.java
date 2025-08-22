package com.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.blog.enums.CommentStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 评论实体类
 */
@TableName("comments")
@Data
@EqualsAndHashCode(callSuper = true)
public class Comment extends BaseEntity {

    @TableField("article_id")
    private Long articleId;

    @TableField("user_id")
    private Long userId;

    @NotBlank(message = "评论内容不能为空")
    @TableField("content")
    private String content;

    @TableField("status")
    private CommentStatus status = CommentStatus.PENDING;
}
