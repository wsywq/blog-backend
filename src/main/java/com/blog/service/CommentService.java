package com.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.dto.CommentDto;
import com.blog.dto.CreateCommentRequest;
import com.blog.entity.Comment;
import com.blog.enums.CommentStatus;

/**
 * 评论服务接口
 */
public interface CommentService {

    /**
     * 根据ID获取评论
     */
    CommentDto getCommentById(Long id);

    /**
     * 根据文章ID获取已批准的评论
     */
    IPage<CommentDto> getApprovedCommentsByArticleId(Long articleId, Page<Comment> page);

    /**
     * 根据文章ID获取所有评论（管理员用）
     */
    IPage<CommentDto> getAllCommentsByArticleId(Long articleId, Page<Comment> page);

    /**
     * 根据状态获取评论
     */
    IPage<CommentDto> getCommentsByStatus(CommentStatus status, Page<Comment> page);

    /**
     * 根据用户ID获取评论
     */
    IPage<CommentDto> getCommentsByUserId(Long userId, Page<Comment> page);

    /**
     * 创建评论
     */
    CommentDto createComment(CreateCommentRequest request, Long userId);

    /**
     * 更新评论状态
     */
    CommentDto updateCommentStatus(Long id, CommentStatus status);

    /**
     * 删除评论
     */
    void deleteComment(Long id);

    /**
     * 统计文章的评论数量
     */
    long countCommentsByArticleId(Long articleId);

    /**
     * 统计待审核的评论数量
     */
    long countPendingComments();
}

