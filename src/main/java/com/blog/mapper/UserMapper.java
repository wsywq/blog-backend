package com.blog.mapper;

import com.blog.dto.UserDto;
import com.blog.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 用户映射器
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * 将用户实体转换为DTO
     */
    UserDto toDto(User user);

    /**
     * 将DTO转换为用户实体
     */
    User toEntity(UserDto userDto);
}

