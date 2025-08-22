package com.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.dto.CommentDto;
import com.blog.dto.CreateCommentRequest;
import com.blog.entity.Article;
import com.blog.entity.Comment;
import com.blog.entity.User;
import com.blog.enums.CommentStatus;
import com.blog.exception.ResourceNotFoundException;
import com.blog.mapper.ArticleMapper;
import com.blog.mapper.CommentMapper;
import com.blog.mapper.UserMapper;
import com.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 评论服务实现类
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final ArticleMapper articleMapper;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public CommentDto getCommentById(Long id) {
        Comment comment = commentMapper.selectById(id);
        if (comment == null) {
            throw new ResourceNotFoundException("评论不存在，ID: " + id);
        }
        return convertToDto(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public IPage<CommentDto> getApprovedCommentsByArticleId(Long articleId, Page<Comment> page) {
        IPage<Comment> comments = commentMapper.selectByArticleIdAndStatusApproved(page, articleId);
        return comments.convert(this::convertToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public IPage<CommentDto> getAllCommentsByArticleId(Long articleId, Page<Comment> page) {
        IPage<Comment> comments = commentMapper.selectByArticleId(page, articleId);
        return comments.convert(this::convertToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public IPage<CommentDto> getCommentsByStatus(CommentStatus status, Page<Comment> page) {
        IPage<Comment> comments = commentMapper.selectByStatus(page, status);
        return comments.convert(this::convertToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public IPage<CommentDto> getCommentsByUserId(Long userId, Page<Comment> page) {
        IPage<Comment> comments = commentMapper.selectByUserId(page, userId);
        return comments.convert(this::convertToDto);
    }

    @Override
    public CommentDto createComment(CreateCommentRequest request, Long userId) {
        // 验证文章是否存在
        Article article = articleMapper.selectById(request.articleId());
        if (article == null) {
            throw new ResourceNotFoundException("文章不存在，ID: " + request.articleId());
        }

        // 验证用户是否存在
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("用户不存在，ID: " + userId);
        }

        // 创建评论
        Comment comment = new Comment();
        comment.setArticleId(request.articleId());
        comment.setUserId(userId);
        comment.setContent(request.content());
        comment.setStatus(CommentStatus.PENDING);

        commentMapper.insert(comment);
        log.info("创建评论成功，文章ID: {}, 用户ID: {}", request.articleId(), userId);
        return convertToDto(comment);
    }

    @Override
    public CommentDto updateCommentStatus(Long id, CommentStatus status) {
        Comment comment = commentMapper.selectById(id);
        if (comment == null) {
            throw new ResourceNotFoundException("评论不存在，ID: " + id);
        }

        comment.setStatus(status);
        commentMapper.updateById(comment);
        log.info("更新评论状态成功，ID: {}, 状态: {}", id, status);
        return convertToDto(comment);
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = commentMapper.selectById(id);
        if (comment == null) {
            throw new ResourceNotFoundException("评论不存在，ID: " + id);
        }
        commentMapper.deleteById(id);
        log.info("删除评论成功，ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countCommentsByArticleId(Long articleId) {
        return commentMapper.countByArticleIdAndStatusApproved(articleId);
    }

    @Override
    @Transactional(readOnly = true)
    public long countPendingComments() {
        return commentMapper.countByStatus(CommentStatus.PENDING);
    }

    /**
     * 将实体转换为DTO
     */
    private CommentDto convertToDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getArticleId(),
                comment.getUserId(),
                comment.getContent(),
                comment.getStatus(),
                comment.getCreateTime(),
                comment.getUpdateTime()
        );
    }
}

