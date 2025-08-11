package com.blog.mapper;

import com.blog.dto.CategoryDto;
import com.blog.entity.Category;
import org.mapstruct.Mapper;

/**
 * 分类映射器
 * 
 * @author blog
 * @since 2024-01-01
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {
    
    CategoryDto toDto(Category category);
    
    Category toEntity(CategoryDto categoryDto);
}
