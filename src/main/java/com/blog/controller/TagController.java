package com.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.dto.ApiResponse;
import com.blog.dto.TagDto;
import com.blog.entity.Tag;
import com.blog.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 标签控制器
 */
@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
@io.swagger.v3.oas.annotations.tags.Tag(name = "标签管理", description = "标签相关接口")
@Slf4j
public class TagController {

    private final TagService tagService;

    @GetMapping
    @Operation(summary = "获取所有标签", description = "获取所有标签列表")
    public ResponseEntity<ApiResponse<List<TagDto>>> getAllTags() {
        try {
            List<TagDto> tags = tagService.getAllTags();
            return ResponseEntity.ok(ApiResponse.success("获取标签列表成功", tags));
        } catch (Exception e) {
            log.error("获取标签列表失败", e);
            return ResponseEntity.ok(ApiResponse.error("获取标签列表失败: " + e.getMessage()));
        }
    }
    
    @GetMapping("/page")
    @Operation(summary = "分页获取标签")
    public ResponseEntity<ApiResponse<IPage<TagDto>>> getTags(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<Tag> pageParam = new Page<>(page, size);
            IPage<TagDto> tags = tagService.getTags(pageParam);
            return ResponseEntity.ok(ApiResponse.success("获取标签列表成功", tags));
        } catch (Exception e) {
            log.error("获取标签列表失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("获取标签列表失败"));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取标签", description = "根据标签ID获取标签详细信息")
    public ResponseEntity<ApiResponse<TagDto>> getTagById(
            @Parameter(description = "标签ID") @PathVariable Long id) {
        try {
            TagDto tag = tagService.getTagById(id);
            return ResponseEntity.ok(ApiResponse.success("获取标签成功", tag));
        } catch (Exception e) {
            log.error("获取标签失败，ID: {}", id, e);
            return ResponseEntity.ok(ApiResponse.error("获取标签失败: " + e.getMessage()));
        }
    }
    
    @GetMapping("/name/{name}")
    @Operation(summary = "根据名称获取标签")
    public ResponseEntity<ApiResponse<TagDto>> getTagByName(
            @Parameter(description = "标签名称") @PathVariable String name) {
        try {
            TagDto tag = tagService.getTagByName(name);
            if (tag == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("标签不存在"));
            }
            return ResponseEntity.ok(ApiResponse.success("获取标签成功", tag));
        } catch (Exception e) {
            log.error("获取标签失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("获取标签失败"));
        }
    }
    
    @PostMapping
    @Operation(summary = "创建标签")
    public ResponseEntity<ApiResponse<TagDto>> createTag(
            @Parameter(description = "标签信息") @Valid @RequestBody TagDto tagDto) {
        try {
            TagDto createdTag = tagService.createTag(tagDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("创建标签成功", createdTag));
        } catch (Exception e) {
            log.error("创建标签失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("创建标签失败"));
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "更新标签")
    public ResponseEntity<ApiResponse<TagDto>> updateTag(
            @Parameter(description = "标签ID") @PathVariable Long id,
            @Parameter(description = "标签信息") @Valid @RequestBody TagDto tagDto) {
        try {
            TagDto updatedTag = tagService.updateTag(id, tagDto);
            return ResponseEntity.ok(ApiResponse.success("更新标签成功", updatedTag));
        } catch (Exception e) {
            log.error("更新标签失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("更新标签失败"));
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "删除标签")
    public ResponseEntity<ApiResponse<Void>> deleteTag(
            @Parameter(description = "标签ID") @PathVariable Long id) {
        try {
            tagService.deleteTag(id);
            return ResponseEntity.ok(ApiResponse.success("删除标签成功", null));
        } catch (Exception e) {
            log.error("删除标签失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("删除标签失败"));
        }
    }
    
    @GetMapping("/check/{name}")
    @Operation(summary = "检查标签名称是否存在")
    public ResponseEntity<ApiResponse<Boolean>> checkTagNameExists(
            @Parameter(description = "标签名称") @PathVariable String name) {
        try {
            boolean exists = tagService.existsByName(name);
            return ResponseEntity.ok(ApiResponse.success("检查完成", exists));
        } catch (Exception e) {
            log.error("检查标签名称失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("检查标签名称失败"));
        }
    }
}
