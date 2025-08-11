package com.blog.controller;

import com.blog.dto.ApiResponse;
import com.blog.dto.CategoryDto;
import com.blog.entity.Category;
import com.blog.repository.CategoryRepository;
import com.blog.mapper.CategoryMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 分类控制器
 * 
 * @author blog
 * @since 2024-01-01
 */
@RestController
@RequestMapping("/categories")
@Tag(name = "分类管理", description = "分类相关接口")
@Slf4j
public class CategoryController {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private CategoryMapper categoryMapper;
    
    @GetMapping
    @Operation(summary = "获取所有分类")
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getAllCategories() {
        try {
            List<Category> categories = categoryRepository.findAll();
            List<CategoryDto> categoryDtos = categories.stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success("获取分类列表成功", categoryDtos));
        } catch (Exception e) {
            log.error("获取分类列表失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("获取分类列表失败"));
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取分类")
    public ResponseEntity<ApiResponse<CategoryDto>> getCategoryById(@PathVariable Long id) {
        try {
            Category category = categoryRepository.findById(id)
                .orElse(null);
            if (category == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("分类不存在"));
            }
            CategoryDto categoryDto = categoryMapper.toDto(category);
            return ResponseEntity.ok(ApiResponse.success("获取分类成功", categoryDto));
        } catch (Exception e) {
            log.error("获取分类失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("获取分类失败"));
        }
    }
}
