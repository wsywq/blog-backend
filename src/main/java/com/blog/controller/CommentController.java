package com.blog.controller;

import com.blog.dto.ApiResponse;
import com.blog.dto.CommentDto;
import com.blog.dto.CreateCommentRequest;
import com.blog.enums.CommentStatus;
import com.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * 评论控制器
 */
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Tag(name = "评论管理", description = "评论相关接口")
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取评论", description = "根据评论ID获取评论详细信息")
    public ResponseEntity<ApiResponse<CommentDto>> getCommentById(
            @Parameter(description = "评论ID") @PathVariable Long id) {
        try {
            CommentDto comment = commentService.getCommentById(id);
            return ResponseEntity.ok(ApiResponse.success("获取评论成功", comment));
        } catch (Exception e) {
            log.error("获取评论失败，ID: {}", id, e);
            return ResponseEntity.ok(ApiResponse.error("获取评论失败: " + e.getMessage()));
        }
    }

    @GetMapping("/article/{articleId}")
    @Operation(summary = "获取文章评论", description = "获取指定文章的已批准评论")
    public ResponseEntity<ApiResponse<Page<CommentDto>>> getCommentsByArticleId(
            @Parameter(description = "文章ID") @PathVariable Long articleId,
            @PageableDefault(size = 20) Pageable pageable) {
        try {
            Page<CommentDto> comments = commentService.getApprovedCommentsByArticleId(articleId, pageable);
            return ResponseEntity.ok(ApiResponse.success("获取评论成功", comments));
        } catch (Exception e) {
            log.error("获取文章评论失败，文章ID: {}", articleId, e);
            return ResponseEntity.ok(ApiResponse.error("获取评论失败: " + e.getMessage()));
        }
    }

    @GetMapping("/article/{articleId}/all")
    @Operation(summary = "获取文章所有评论（管理员）", description = "获取指定文章的所有评论，包括待审核的")
    public ResponseEntity<ApiResponse<Page<CommentDto>>> getAllCommentsByArticleId(
            @Parameter(description = "文章ID") @PathVariable Long articleId,
            @PageableDefault(size = 20) Pageable pageable) {
        try {
            Page<CommentDto> comments = commentService.getAllCommentsByArticleId(articleId, pageable);
            return ResponseEntity.ok(ApiResponse.success("获取评论成功", comments));
        } catch (Exception e) {
            log.error("获取文章所有评论失败，文章ID: {}", articleId, e);
            return ResponseEntity.ok(ApiResponse.error("获取评论失败: " + e.getMessage()));
        }
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "根据状态获取评论", description = "根据评论状态获取评论列表")
    public ResponseEntity<ApiResponse<Page<CommentDto>>> getCommentsByStatus(
            @Parameter(description = "评论状态") @PathVariable CommentStatus status,
            @PageableDefault(size = 20) Pageable pageable) {
        try {
            Page<CommentDto> comments = commentService.getCommentsByStatus(status, pageable);
            return ResponseEntity.ok(ApiResponse.success("获取评论成功", comments));
        } catch (Exception e) {
            log.error("获取评论失败，状态: {}", status, e);
            return ResponseEntity.ok(ApiResponse.error("获取评论失败: " + e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "获取用户评论", description = "获取指定用户的所有评论")
    public ResponseEntity<ApiResponse<Page<CommentDto>>> getCommentsByUserId(
            @Parameter(description = "用户ID") @PathVariable Long userId,
            @PageableDefault(size = 20) Pageable pageable) {
        try {
            Page<CommentDto> comments = commentService.getCommentsByUserId(userId, pageable);
            return ResponseEntity.ok(ApiResponse.success("获取评论成功", comments));
        } catch (Exception e) {
            log.error("获取用户评论失败，用户ID: {}", userId, e);
            return ResponseEntity.ok(ApiResponse.error("获取评论失败: " + e.getMessage()));
        }
    }

    @PostMapping
    @Operation(summary = "创建评论", description = "创建新评论")
    public ResponseEntity<ApiResponse<CommentDto>> createComment(
            @Parameter(description = "评论信息") @Valid @RequestBody CreateCommentRequest request,
            @Parameter(description = "用户ID") @RequestParam Long userId) {
        try {
            CommentDto createdComment = commentService.createComment(request, userId);
            return ResponseEntity.ok(ApiResponse.success("创建评论成功", createdComment));
        } catch (Exception e) {
            log.error("创建评论失败", e);
            return ResponseEntity.ok(ApiResponse.error("创建评论失败: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "更新评论状态", description = "更新评论的审核状态")
    public ResponseEntity<ApiResponse<CommentDto>> updateCommentStatus(
            @Parameter(description = "评论ID") @PathVariable Long id,
            @Parameter(description = "评论状态") @RequestParam CommentStatus status) {
        try {
            CommentDto updatedComment = commentService.updateCommentStatus(id, status);
            return ResponseEntity.ok(ApiResponse.success("更新评论状态成功", updatedComment));
        } catch (Exception e) {
            log.error("更新评论状态失败，ID: {}", id, e);
            return ResponseEntity.ok(ApiResponse.error("更新评论状态失败: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除评论", description = "删除指定评论")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @Parameter(description = "评论ID") @PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return ResponseEntity.ok(ApiResponse.success("删除评论成功", null));
        } catch (Exception e) {
            log.error("删除评论失败，ID: {}", id, e);
            return ResponseEntity.ok(ApiResponse.error("删除评论失败: " + e.getMessage()));
        }
    }

    @GetMapping("/count/article/{articleId}")
    @Operation(summary = "统计文章评论数", description = "统计指定文章的已批准评论数量")
    public ResponseEntity<ApiResponse<Long>> countCommentsByArticleId(
            @Parameter(description = "文章ID") @PathVariable Long articleId) {
        try {
            long count = commentService.countCommentsByArticleId(articleId);
            return ResponseEntity.ok(ApiResponse.success("统计成功", count));
        } catch (Exception e) {
            log.error("统计评论数失败，文章ID: {}", articleId, e);
            return ResponseEntity.ok(ApiResponse.error("统计失败: " + e.getMessage()));
        }
    }

    @GetMapping("/count/pending")
    @Operation(summary = "统计待审核评论数", description = "统计待审核的评论数量")
    public ResponseEntity<ApiResponse<Long>> countPendingComments() {
        try {
            long count = commentService.countPendingComments();
            return ResponseEntity.ok(ApiResponse.success("统计成功", count));
        } catch (Exception e) {
            log.error("统计待审核评论数失败", e);
            return ResponseEntity.ok(ApiResponse.error("统计失败: " + e.getMessage()));
        }
    }
}

