package com.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.dto.UserDto;
import com.blog.entity.User;

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
    IPage<UserDto> getAllUsers(Page<User> page);

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
     * 根据用户名或邮箱获取用户
     */
    Optional<UserDto> getUserByUsernameOrEmail(String usernameOrEmail);

    /**
     * 通过GitHub OAuth创建或更新用户
     */
    UserDto createOrUpdateUserByGithubId(String githubId, String username, String email, String avatar);
}

