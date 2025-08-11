package com.blog.repository;

import com.blog.entity.Comment;
import com.blog.enums.CommentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评论数据访问层
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 根据文章ID查找已批准的评论
     */
    @Query("SELECT c FROM Comment c WHERE c.article.id = :articleId AND c.status = 'APPROVED' ORDER BY c.createTime DESC")
    Page<Comment> findByArticleIdAndStatusApproved(@Param("articleId") Long articleId, Pageable pageable);

    /**
     * 根据文章ID查找所有评论
     */
    @Query("SELECT c FROM Comment c WHERE c.article.id = :articleId ORDER BY c.createTime DESC")
    Page<Comment> findByArticleId(@Param("articleId") Long articleId, Pageable pageable);

    /**
     * 根据状态查找评论
     */
    Page<Comment> findByStatus(CommentStatus status, Pageable pageable);

    /**
     * 根据用户ID查找评论
     */
    @Query("SELECT c FROM Comment c WHERE c.user.id = :userId ORDER BY c.createTime DESC")
    Page<Comment> findByUserId(@Param("userId") Long userId, Pageable pageable);

    /**
     * 统计文章的评论数量
     */
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.article.id = :articleId AND c.status = 'APPROVED'")
    long countByArticleIdAndStatusApproved(@Param("articleId") Long articleId);

    /**
     * 统计待审核的评论数量
     */
    long countByStatus(CommentStatus status);

    /**
     * 根据文章ID删除评论
     */
    void deleteByArticleId(Long articleId);

    /**
     * 根据用户ID删除评论
     */
    void deleteByUserId(Long userId);
}

