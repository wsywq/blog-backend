package com.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.dto.CategoryDto;
import com.blog.entity.Category;

import java.util.List;

/**
 * 分类服务接口
 */
public interface CategoryService {

    /**
     * 获取所有分类
     */
    List<CategoryDto> getAllCategories();

    /**
     * 分页获取分类
     */
    IPage<CategoryDto> getCategories(Page<Category> page);

    /**
     * 根据ID获取分类
     */
    CategoryDto getCategoryById(Long id);

    /**
     * 根据名称获取分类
     */
    CategoryDto getCategoryByName(String name);

    /**
     * 创建分类
     */
    CategoryDto createCategory(CategoryDto categoryDto);

    /**
     * 更新分类
     */
    CategoryDto updateCategory(Long id, CategoryDto categoryDto);

    /**
     * 删除分类
     */
    void deleteCategory(Long id);

    /**
     * 检查分类名称是否存在
     */
    boolean existsByName(String name);
}
