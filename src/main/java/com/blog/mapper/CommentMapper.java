package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.entity.Comment;
import com.blog.enums.CommentStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 评论数据访问层
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 根据文章ID查找已批准的评论
     */
    IPage<Comment> selectByArticleIdAndStatusApproved(Page<Comment> page, @Param("articleId") Long articleId);

    /**
     * 根据文章ID查找所有评论
     */
    IPage<Comment> selectByArticleId(Page<Comment> page, @Param("articleId") Long articleId);

    /**
     * 根据状态查找评论
     */
    IPage<Comment> selectByStatus(Page<Comment> page, @Param("status") CommentStatus status);

    /**
     * 根据用户ID查找评论
     */
    IPage<Comment> selectByUserId(Page<Comment> page, @Param("userId") Long userId);

    /**
     * 统计文章的评论数量
     */
    long countByArticleIdAndStatusApproved(@Param("articleId") Long articleId);

    /**
     * 统计待审核的评论数量
     */
    long countByStatus(@Param("status") CommentStatus status);

    /**
     * 根据文章ID删除评论
     */
    int deleteByArticleId(@Param("articleId") Long articleId);

    /**
     * 根据用户ID删除评论
     */
    int deleteByUserId(@Param("userId") Long userId);
}

