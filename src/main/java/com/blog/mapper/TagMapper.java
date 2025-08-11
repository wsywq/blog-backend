package com.blog.mapper;

import com.blog.dto.TagDto;
import com.blog.entity.Tag;
import org.mapstruct.Mapper;

/**
 * 标签映射器
 * 
 * @author blog
 * @since 2024-01-01
 */
@Mapper(componentModel = "spring")
public interface TagMapper {
    
    TagDto toDto(Tag tag);
    
    Tag toEntity(TagDto tagDto);
}
