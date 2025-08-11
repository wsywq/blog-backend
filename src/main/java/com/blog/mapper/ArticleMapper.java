package com.blog.mapper;

import com.blog.dto.ArticleDto;
import com.blog.entity.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 文章映射器
 * 
 * @author blog
 * @since 2024-01-01
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class, TagMapper.class})
public interface ArticleMapper {
    
    @Mapping(target = "category", source = "category")
    @Mapping(target = "tags", source = "tags")
    ArticleDto toDto(Article article);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "viewCount", constant = "0L")
    @Mapping(target = "comments", ignore = true)
    Article toEntity(ArticleDto articleDto);
}
