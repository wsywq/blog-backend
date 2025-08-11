package com.blog.service.impl;

import com.blog.dto.CommentDto;
import com.blog.dto.CreateCommentRequest;
import com.blog.entity.Article;
import com.blog.entity.Comment;
import com.blog.entity.User;
import com.blog.enums.CommentStatus;
import com.blog.exception.ResourceNotFoundException;
import com.blog.mapper.CommentMapper;
import com.blog.repository.ArticleRepository;
import com.blog.repository.CommentRepository;
import com.blog.repository.UserRepository;
import com.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Override
    @Transactional(readOnly = true)
    public CommentDto getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("评论不存在，ID: " + id));
        return commentMapper.toDto(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommentDto> getApprovedCommentsByArticleId(Long articleId, Pageable pageable) {
        return commentRepository.findByArticleIdAndStatusApproved(articleId, pageable)
                .map(commentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommentDto> getAllCommentsByArticleId(Long articleId, Pageable pageable) {
        return commentRepository.findByArticleId(articleId, pageable)
                .map(commentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommentDto> getCommentsByStatus(CommentStatus status, Pageable pageable) {
        return commentRepository.findByStatus(status, pageable)
                .map(commentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommentDto> getCommentsByUserId(Long userId, Pageable pageable) {
        return commentRepository.findByUserId(userId, pageable)
                .map(commentMapper::toDto);
    }

    @Override
    public CommentDto createComment(CreateCommentRequest request, Long userId) {
        // 验证文章是否存在
        Article article = articleRepository.findById(request.articleId())
                .orElseThrow(() -> new ResourceNotFoundException("文章不存在，ID: " + request.articleId()));

        // 验证用户是否存在
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("用户不存在，ID: " + userId));

        // 创建评论
        Comment comment = new Comment();
        comment.setArticle(article);
        comment.setUser(user);
        comment.setContent(request.content());
        comment.setStatus(CommentStatus.PENDING);

        Comment savedComment = commentRepository.save(comment);
        log.info("创建评论成功，文章ID: {}, 用户ID: {}", request.articleId(), userId);
        return commentMapper.toDto(savedComment);
    }

    @Override
    public CommentDto updateCommentStatus(Long id, CommentStatus status) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("评论不存在，ID: " + id));

        comment.setStatus(status);
        Comment updatedComment = commentRepository.save(comment);
        log.info("更新评论状态成功，ID: {}, 状态: {}", id, status);
        return commentMapper.toDto(updatedComment);
    }

    @Override
    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new ResourceNotFoundException("评论不存在，ID: " + id);
        }
        commentRepository.deleteById(id);
        log.info("删除评论成功，ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countCommentsByArticleId(Long articleId) {
        return commentRepository.countByArticleIdAndStatusApproved(articleId);
    }

    @Override
    @Transactional(readOnly = true)
    public long countPendingComments() {
        return commentRepository.countByStatus(CommentStatus.PENDING);
    }
}

