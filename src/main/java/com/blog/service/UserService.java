package com.blog.service;

import com.blog.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 根据ID获取用户
     */
    UserDto getUserById(Long id);

    /**
     * 根据用户名获取用户
     */
    Optional<UserDto> getUserByUsername(String username);

    /**
     * 根据邮箱获取用户
     */
    Optional<UserDto> getUserByEmail(String email);

    /**
     * 根据GitHub ID获取用户
     */
    Optional<UserDto> getUserByGithubId(String githubId);

    /**
     * 分页获取所有用户
     */
    Page<UserDto> getAllUsers(Pageable pageable);

    /**
     * 创建用户
     */
    UserDto createUser(UserDto userDto);

    /**
     * 更新用户
     */
    UserDto updateUser(Long id, UserDto userDto);

    /**
     * 删除用户
     */
    void deleteUser(Long id);

    /**
     * 检查用户名是否存在
     */
    boolean existsByUsername(String username);

    /**
     * 检查邮箱是否存在
     */
    boolean existsByEmail(String email);

    /**
     * 通过GitHub OAuth创建或更新用户
     */
    UserDto createOrUpdateUserByGithub(String githubId, String username, String email, String avatar);
}

