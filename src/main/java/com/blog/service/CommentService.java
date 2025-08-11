package com.blog.service;

import com.blog.dto.CommentDto;
import com.blog.dto.CreateCommentRequest;
import com.blog.enums.CommentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    Page<CommentDto> getApprovedCommentsByArticleId(Long articleId, Pageable pageable);

    /**
     * 根据文章ID获取所有评论（管理员用）
     */
    Page<CommentDto> getAllCommentsByArticleId(Long articleId, Pageable pageable);

    /**
     * 根据状态获取评论
     */
    Page<CommentDto> getCommentsByStatus(CommentStatus status, Pageable pageable);

    /**
     * 根据用户ID获取评论
     */
    Page<CommentDto> getCommentsByUserId(Long userId, Pageable pageable);

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

