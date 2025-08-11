package com.blog.controller;

import com.blog.dto.ApiResponse;
import com.blog.dto.ArticleDto;
import com.blog.dto.CreateArticleRequest;
import com.blog.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 文章控制器
 * 
 * @author blog
 * @since 2024-01-01
 */
@RestController
@RequestMapping("/articles")
@Tag(name = "文章管理", description = "文章相关接口")
@Slf4j
public class ArticleController {
    
    @Autowired
    private ArticleService articleService;
    
    @GetMapping
    @Operation(summary = "获取文章列表")
    public ResponseEntity<ApiResponse<Page<ArticleDto>>> getArticles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createTime") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        try {
            Page<ArticleDto> articles = articleService.getArticles(page, size, sortBy, sortDir);
            return ResponseEntity.ok(ApiResponse.success("获取文章列表成功", articles));
        } catch (Exception e) {
            log.error("获取文章列表失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("获取文章列表失败"));
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "获取文章详情")
    public ResponseEntity<ApiResponse<ArticleDto>> getArticleById(@PathVariable Long id) {
        try {
            ArticleDto article = articleService.getArticleById(id);
            return ResponseEntity.ok(ApiResponse.success("获取文章详情成功", article));
        } catch (Exception e) {
            log.error("获取文章详情失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("获取文章详情失败"));
        }
    }
    
    @PostMapping
    @Operation(summary = "创建文章")
    public ResponseEntity<ApiResponse<ArticleDto>> createArticle(
            @Valid @RequestBody CreateArticleRequest request) {
        try {
            ArticleDto article = articleService.createArticle(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("创建文章成功", article));
        } catch (Exception e) {
            log.error("创建文章失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("创建文章失败"));
        }
    }
    
    @GetMapping("/category/{categoryId}")
    @Operation(summary = "根据分类获取文章")
    public ResponseEntity<ApiResponse<Page<ArticleDto>>> getArticlesByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<ArticleDto> articles = articleService.getArticlesByCategory(categoryId, page, size);
            return ResponseEntity.ok(ApiResponse.success("获取分类文章成功", articles));
        } catch (Exception e) {
            log.error("获取分类文章失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("获取分类文章失败"));
        }
    }
    
    @GetMapping("/tag/{tagId}")
    @Operation(summary = "根据标签获取文章")
    public ResponseEntity<ApiResponse<Page<ArticleDto>>> getArticlesByTag(
            @PathVariable Long tagId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<ArticleDto> articles = articleService.getArticlesByTag(tagId, page, size);
            return ResponseEntity.ok(ApiResponse.success("获取标签文章成功", articles));
        } catch (Exception e) {
            log.error("获取标签文章失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("获取标签文章失败"));
        }
    }
    
    @GetMapping("/search")
    @Operation(summary = "搜索文章")
    public ResponseEntity<ApiResponse<Page<ArticleDto>>> searchArticles(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<ArticleDto> articles = articleService.searchArticles(keyword, page, size);
            return ResponseEntity.ok(ApiResponse.success("搜索文章成功", articles));
        } catch (Exception e) {
            log.error("搜索文章失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("搜索文章失败"));
        }
    }
    
    @GetMapping("/popular")
    @Operation(summary = "获取热门文章")
    public ResponseEntity<ApiResponse<List<ArticleDto>>> getPopularArticles(
            @RequestParam(defaultValue = "5") int limit) {
        try {
            List<ArticleDto> articles = articleService.getPopularArticles(limit);
            return ResponseEntity.ok(ApiResponse.success("获取热门文章成功", articles));
        } catch (Exception e) {
            log.error("获取热门文章失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("获取热门文章失败"));
        }
    }
    
    @PutMapping("/{id}/view")
    @Operation(summary = "更新文章访问量")
    public ResponseEntity<ApiResponse<Void>> incrementViewCount(@PathVariable Long id) {
        try {
            articleService.incrementViewCount(id);
            return ResponseEntity.ok(ApiResponse.success("更新访问量成功", null));
        } catch (Exception e) {
            log.error("更新访问量失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("更新访问量失败"));
        }
    }
}
