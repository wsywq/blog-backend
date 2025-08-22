package com.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.dto.CategoryDto;
import com.blog.entity.Category;
import com.blog.exception.ResourceNotFoundException;
import com.blog.mapper.CategoryMapper;
import com.blog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 分类服务实现类
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryMapper.selectList(null);
        return categories.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public IPage<CategoryDto> getCategories(Page<Category> page) {
        IPage<Category> categoryPage = categoryMapper.selectPage(page, null);
        return categoryPage.convert(this::convertToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryMapper.selectById(id);
        if (category == null) {
            throw new ResourceNotFoundException("分类不存在，ID: " + id);
        }
        return convertToDto(category);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategoryByName(String name) {
        Category category = categoryMapper.selectByName(name);
        return category != null ? convertToDto(category) : null;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        // 检查分类名称是否已存在
        if (categoryMapper.existsByName(categoryDto.name())) {
            throw new IllegalArgumentException("分类名称已存在: " + categoryDto.name());
        }

        Category category = convertToEntity(categoryDto);
        categoryMapper.insert(category);
        log.info("创建分类成功: {}", category.getName());
        return convertToDto(category);
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category existingCategory = categoryMapper.selectById(id);
        if (existingCategory == null) {
            throw new ResourceNotFoundException("分类不存在，ID: " + id);
        }

        // 检查分类名称是否被其他分类使用
        Category categoryByName = categoryMapper.selectByName(categoryDto.name());
        if (categoryByName != null && !categoryByName.getId().equals(id)) {
            throw new IllegalArgumentException("分类名称已存在: " + categoryDto.name());
        }

        existingCategory.setName(categoryDto.name());
        existingCategory.setDescription(categoryDto.description());
        existingCategory.setIcon(categoryDto.icon());

        categoryMapper.updateById(existingCategory);
        log.info("更新分类成功: {}", existingCategory.getName());
        return convertToDto(existingCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryMapper.selectById(id);
        if (category == null) {
            throw new ResourceNotFoundException("分类不存在，ID: " + id);
        }

        categoryMapper.deleteById(id);
        log.info("删除分类成功: {}", category.getName());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return categoryMapper.existsByName(name);
    }

    /**
     * 将实体转换为DTO
     */
    private CategoryDto convertToDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getIcon(),
                category.getCreateTime(),
                category.getUpdateTime()
        );
    }

    /**
     * 将DTO转换为实体
     */
    private Category convertToEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.name());
        category.setDescription(categoryDto.description());
        category.setIcon(categoryDto.icon());
        return category;
    }
}
