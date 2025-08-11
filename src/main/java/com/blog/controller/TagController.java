package com.blog.controller;

import com.blog.dto.ApiResponse;
import com.blog.dto.TagDto;
import com.blog.entity.Tag;
import com.blog.repository.TagRepository;
import com.blog.mapper.TagMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 标签控制器
 */
@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
@io.swagger.v3.oas.annotations.tags.Tag(name = "标签管理", description = "标签相关接口")
@Slf4j
public class TagController {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @GetMapping
    @Operation(summary = "获取所有标签", description = "获取所有标签列表")
    public ResponseEntity<ApiResponse<List<TagDto>>> getAllTags() {
        try {
            List<TagDto> tags = tagRepository.findAll().stream()
                    .map(tagMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success("获取标签列表成功", tags));
        } catch (Exception e) {
            log.error("获取标签列表失败", e);
            return ResponseEntity.ok(ApiResponse.error("获取标签列表失败: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取标签", description = "根据标签ID获取标签详细信息")
    public ResponseEntity<ApiResponse<TagDto>> getTagById(
            @Parameter(description = "标签ID") @PathVariable Long id) {
        try {
            Tag tag = tagRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("标签不存在，ID: " + id));
            TagDto tagDto = tagMapper.toDto(tag);
            return ResponseEntity.ok(ApiResponse.success("获取标签成功", tagDto));
        } catch (Exception e) {
            log.error("获取标签失败，ID: {}", id, e);
            return ResponseEntity.ok(ApiResponse.error("获取标签失败: " + e.getMessage()));
        }
    }
}
