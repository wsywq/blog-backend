package com.blog.mapper;

import com.blog.dto.CommentDto;
import com.blog.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 评论映射器
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CommentMapper {

    /**
     * 将评论实体转换为DTO
     */
    @Mapping(source = "article.id", target = "articleId")
    @Mapping(source = "user", target = "user")
    CommentDto toDto(Comment comment);

    /**
     * 将DTO转换为评论实体
     */
    @Mapping(target = "article", ignore = true)
    @Mapping(target = "user", ignore = true)
    Comment toEntity(CommentDto commentDto);
}

