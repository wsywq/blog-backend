package com.blog.service.impl;

import com.blog.dto.UserDto;
import com.blog.entity.User;
import com.blog.enums.UserRole;
import com.blog.exception.ResourceNotFoundException;
import com.blog.mapper.UserMapper;
import com.blog.repository.UserRepository;
import com.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("用户不存在，ID: " + id));
        return userMapper.toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> getUserByGithubId(String githubId) {
        return userRepository.findByGithubId(githubId)
                .map(userMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toDto);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        // 检查用户名是否已存在
        if (userRepository.existsByUsername(userDto.username())) {
            throw new IllegalArgumentException("用户名已存在: " + userDto.username());
        }

        // 检查邮箱是否已存在
        if (userRepository.existsByEmail(userDto.email())) {
            throw new IllegalArgumentException("邮箱已存在: " + userDto.email());
        }

        User user = userMapper.toEntity(userDto);
        User savedUser = userRepository.save(user);
        log.info("创建用户成功: {}", savedUser.getUsername());
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("用户不存在，ID: " + id));

        // 检查用户名是否被其他用户使用
        if (!existingUser.getUsername().equals(userDto.username()) &&
                userRepository.existsByUsername(userDto.username())) {
            throw new IllegalArgumentException("用户名已存在: " + userDto.username());
        }

        // 检查邮箱是否被其他用户使用
        if (!existingUser.getEmail().equals(userDto.email()) &&
                userRepository.existsByEmail(userDto.email())) {
            throw new IllegalArgumentException("邮箱已存在: " + userDto.email());
        }

        // 更新用户信息
        existingUser.setUsername(userDto.username());
        existingUser.setEmail(userDto.email());
        existingUser.setAvatar(userDto.avatar());
        existingUser.setGithubId(userDto.githubId());
        existingUser.setRole(userDto.role());

        User updatedUser = userRepository.save(existingUser);
        log.info("更新用户成功: {}", updatedUser.getUsername());
        return userMapper.toDto(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("用户不存在，ID: " + id);
        }
        userRepository.deleteById(id);
        log.info("删除用户成功，ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDto createOrUpdateUserByGithub(String githubId, String username, String email, String avatar) {
        // 先尝试根据GitHub ID查找用户
        Optional<User> existingUser = userRepository.findByGithubId(githubId);
        
        if (existingUser.isPresent()) {
            // 更新现有用户信息
            User user = existingUser.get();
            user.setUsername(username);
            user.setEmail(email);
            user.setAvatar(avatar);
            
            User updatedUser = userRepository.save(user);
            log.info("通过GitHub更新用户成功: {}", updatedUser.getUsername());
            return userMapper.toDto(updatedUser);
        } else {
            // 创建新用户
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setAvatar(avatar);
            newUser.setGithubId(githubId);
            newUser.setRole(UserRole.USER);
            
            User savedUser = userRepository.save(newUser);
            log.info("通过GitHub创建用户成功: {}", savedUser.getUsername());
            return userMapper.toDto(savedUser);
        }
    }
}

