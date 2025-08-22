package com.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.dto.ApiResponse;
import com.blog.dto.CategoryDto;
import com.blog.entity.Category;
import com.blog.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 分类控制器
 * 
 * @author blog
 * @since 2024-01-01
 */
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "分类管理", description = "分类相关接口")
@Slf4j
public class CategoryController {
    
    private final CategoryService categoryService;
    
    @GetMapping
    @Operation(summary = "获取所有分类")
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getAllCategories() {
        try {
            List<CategoryDto> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(ApiResponse.success("获取分类列表成功", categories));
        } catch (Exception e) {
            log.error("获取分类列表失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("获取分类列表失败"));
        }
    }
    
    @GetMapping("/page")
    @Operation(summary = "分页获取分类")
    public ResponseEntity<ApiResponse<IPage<CategoryDto>>> getCategories(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<Category> pageParam = new Page<>(page, size);
            IPage<CategoryDto> categories = categoryService.getCategories(pageParam);
            return ResponseEntity.ok(ApiResponse.success("获取分类列表成功", categories));
        } catch (Exception e) {
            log.error("获取分类列表失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("获取分类列表失败"));
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取分类")
    public ResponseEntity<ApiResponse<CategoryDto>> getCategoryById(
            @Parameter(description = "分类ID") @PathVariable Long id) {
        try {
            CategoryDto category = categoryService.getCategoryById(id);
            return ResponseEntity.ok(ApiResponse.success("获取分类成功", category));
        } catch (Exception e) {
            log.error("获取分类失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("获取分类失败"));
        }
    }
    
    @GetMapping("/name/{name}")
    @Operation(summary = "根据名称获取分类")
    public ResponseEntity<ApiResponse<CategoryDto>> getCategoryByName(
            @Parameter(description = "分类名称") @PathVariable String name) {
        try {
            CategoryDto category = categoryService.getCategoryByName(name);
            if (category == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("分类不存在"));
            }
            return ResponseEntity.ok(ApiResponse.success("获取分类成功", category));
        } catch (Exception e) {
            log.error("获取分类失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("获取分类失败"));
        }
    }
    
    @PostMapping
    @Operation(summary = "创建分类")
    public ResponseEntity<ApiResponse<CategoryDto>> createCategory(
            @Parameter(description = "分类信息") @Valid @RequestBody CategoryDto categoryDto) {
        try {
            CategoryDto createdCategory = categoryService.createCategory(categoryDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("创建分类成功", createdCategory));
        } catch (Exception e) {
            log.error("创建分类失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("创建分类失败"));
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "更新分类")
    public ResponseEntity<ApiResponse<CategoryDto>> updateCategory(
            @Parameter(description = "分类ID") @PathVariable Long id,
            @Parameter(description = "分类信息") @Valid @RequestBody CategoryDto categoryDto) {
        try {
            CategoryDto updatedCategory = categoryService.updateCategory(id, categoryDto);
            return ResponseEntity.ok(ApiResponse.success("更新分类成功", updatedCategory));
        } catch (Exception e) {
            log.error("更新分类失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("更新分类失败"));
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "删除分类")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(
            @Parameter(description = "分类ID") @PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok(ApiResponse.success("删除分类成功", null));
        } catch (Exception e) {
            log.error("删除分类失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("删除分类失败"));
        }
    }
    
    @GetMapping("/check/{name}")
    @Operation(summary = "检查分类名称是否存在")
    public ResponseEntity<ApiResponse<Boolean>> checkCategoryNameExists(
            @Parameter(description = "分类名称") @PathVariable String name) {
        try {
            boolean exists = categoryService.existsByName(name);
            return ResponseEntity.ok(ApiResponse.success("检查完成", exists));
        } catch (Exception e) {
            log.error("检查分类名称失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("检查分类名称失败"));
        }
    }
}
