package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.dto.UserDto;
import com.blog.entity.User;
import com.blog.enums.UserRole;
import com.blog.exception.ResourceNotFoundException;
import com.blog.mapper.UserMapper;
import com.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 用户服务实现类
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new ResourceNotFoundException("用户不存在，ID: " + id);
        }
        return convertToDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> getUserByUsername(String username) {
        User user = userMapper.selectByUsername(username);
        return Optional.ofNullable(user).map(this::convertToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> getUserByEmail(String email) {
        User user = userMapper.selectByEmail(email);
        return Optional.ofNullable(user).map(this::convertToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> getUserByGithubId(String githubId) {
        User user = userMapper.selectByGithubId(githubId);
        return Optional.ofNullable(user).map(this::convertToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public IPage<UserDto> getAllUsers(Page<User> page) {
        IPage<User> userPage = userMapper.selectPage(page, null);
        return userPage.convert(this::convertToDto);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        // 检查用户名是否已存在
        if (userMapper.selectByUsername(userDto.username()) != null) {
            throw new IllegalArgumentException("用户名已存在: " + userDto.username());
        }

        // 检查邮箱是否已存在
        if (userMapper.selectByEmail(userDto.email()) != null) {
            throw new IllegalArgumentException("邮箱已存在: " + userDto.email());
        }

        User user = convertToEntity(userDto);
        userMapper.insert(user);
        log.info("创建用户成功: {}", user.getUsername());
        return convertToDto(user);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User existingUser = userMapper.selectById(id);
        if (existingUser == null) {
            throw new ResourceNotFoundException("用户不存在，ID: " + id);
        }

        // 检查用户名是否被其他用户使用
        User userByUsername = userMapper.selectByUsername(userDto.username());
        if (userByUsername != null && !userByUsername.getId().equals(id)) {
            throw new IllegalArgumentException("用户名已存在: " + userDto.username());
        }

        // 检查邮箱是否被其他用户使用
        User userByEmail = userMapper.selectByEmail(userDto.email());
        if (userByEmail != null && !userByEmail.getId().equals(id)) {
            throw new IllegalArgumentException("邮箱已存在: " + userDto.email());
        }

        // 更新用户信息
        existingUser.setUsername(userDto.username());
        existingUser.setEmail(userDto.email());
        existingUser.setAvatar(userDto.avatar());
        existingUser.setGithubId(userDto.githubId());
        existingUser.setRole(userDto.role());

        userMapper.updateById(existingUser);
        log.info("更新用户成功: {}", existingUser.getUsername());
        return convertToDto(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new ResourceNotFoundException("用户不存在，ID: " + id);
        }

        userMapper.deleteById(id);
        log.info("删除用户成功: {}", user.getUsername());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userMapper.selectByUsername(username) != null;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userMapper.selectByEmail(email) != null;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> getUserByUsernameOrEmail(String usernameOrEmail) {
        User user = userMapper.selectByUsernameOrEmail(usernameOrEmail);
        return Optional.ofNullable(user).map(this::convertToDto);
    }

    @Override
    public UserDto createOrUpdateUserByGithubId(String githubId, String username, String email, String avatar) {
        User existingUser = userMapper.selectByGithubId(githubId);
        
        if (existingUser != null) {
            // 更新现有用户信息
            existingUser.setUsername(username);
            existingUser.setEmail(email);
            existingUser.setAvatar(avatar);
            userMapper.updateById(existingUser);
            log.info("更新GitHub用户信息: {}", username);
            return convertToDto(existingUser);
        } else {
            // 创建新用户
            User newUser = new User();
            newUser.setGithubId(githubId);
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setAvatar(avatar);
            newUser.setRole(UserRole.USER);
            
            userMapper.insert(newUser);
            log.info("创建GitHub用户: {}", username);
            return convertToDto(newUser);
        }
    }

    /**
     * 将实体转换为DTO
     */
    private UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getAvatar(),
                user.getGithubId(),
                user.getRole(),
                user.getCreateTime(),
                user.getUpdateTime()
        );
    }

    /**
     * 将DTO转换为实体
     */
    private User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.username());
        user.setEmail(userDto.email());
        user.setAvatar(userDto.avatar());
        user.setGithubId(userDto.githubId());
        user.setRole(userDto.role());
        return user;
    }
}

