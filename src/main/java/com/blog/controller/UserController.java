package com.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.dto.ApiResponse;
import com.blog.dto.UserDto;
import com.blog.entity.User;
import com.blog.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "用户管理", description = "用户相关接口")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "获取用户列表", description = "分页获取所有用户")
    public ResponseEntity<ApiResponse<IPage<UserDto>>> getUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        try {
            Page<User> pageParam = new Page<>(page, size);
            IPage<UserDto> users = userService.getAllUsers(pageParam);
            return ResponseEntity.ok(ApiResponse.success("获取用户列表成功", users));
        } catch (Exception e) {
            log.error("获取用户列表失败", e);
            return ResponseEntity.ok(ApiResponse.error("获取用户列表失败: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取用户", description = "根据用户ID获取用户详细信息")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(
            @Parameter(description = "用户ID") @PathVariable Long id) {
        try {
            UserDto user = userService.getUserById(id);
            return ResponseEntity.ok(ApiResponse.success("获取用户成功", user));
        } catch (Exception e) {
            log.error("获取用户失败，ID: {}", id, e);
            return ResponseEntity.ok(ApiResponse.error("获取用户失败: " + e.getMessage()));
        }
    }

    @GetMapping("/username/{username}")
    @Operation(summary = "根据用户名获取用户", description = "根据用户名获取用户信息")
    public ResponseEntity<ApiResponse<UserDto>> getUserByUsername(
            @Parameter(description = "用户名") @PathVariable String username) {
        try {
            return userService.getUserByUsername(username)
                    .map(user -> ResponseEntity.ok(ApiResponse.success("获取用户成功", user)))
                    .orElse(ResponseEntity.ok(ApiResponse.error("用户不存在")));
        } catch (Exception e) {
            log.error("获取用户失败，用户名: {}", username, e);
            return ResponseEntity.ok(ApiResponse.error("获取用户失败: " + e.getMessage()));
        }
    }

    @PostMapping
    @Operation(summary = "创建用户", description = "创建新用户")
    public ResponseEntity<ApiResponse<UserDto>> createUser(
            @Parameter(description = "用户信息") @Valid @RequestBody UserDto userDto) {
        try {
            UserDto createdUser = userService.createUser(userDto);
            return ResponseEntity.ok(ApiResponse.success("创建用户成功", createdUser));
        } catch (Exception e) {
            log.error("创建用户失败", e);
            return ResponseEntity.ok(ApiResponse.error("创建用户失败: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新用户", description = "更新用户信息")
    public ResponseEntity<ApiResponse<UserDto>> updateUser(
            @Parameter(description = "用户ID") @PathVariable Long id,
            @Parameter(description = "用户信息") @Valid @RequestBody UserDto userDto) {
        try {
            UserDto updatedUser = userService.updateUser(id, userDto);
            return ResponseEntity.ok(ApiResponse.success("更新用户成功", updatedUser));
        } catch (Exception e) {
            log.error("更新用户失败，ID: {}", id, e);
            return ResponseEntity.ok(ApiResponse.error("更新用户失败: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户", description = "删除指定用户")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @Parameter(description = "用户ID") @PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(ApiResponse.success("删除用户成功", null));
        } catch (Exception e) {
            log.error("删除用户失败，ID: {}", id, e);
            return ResponseEntity.ok(ApiResponse.error("删除用户失败: " + e.getMessage()));
        }
    }

    @GetMapping("/check/username/{username}")
    @Operation(summary = "检查用户名是否存在", description = "检查指定用户名是否已被使用")
    public ResponseEntity<ApiResponse<Boolean>> checkUsernameExists(
            @Parameter(description = "用户名") @PathVariable String username) {
        try {
            boolean exists = userService.existsByUsername(username);
            return ResponseEntity.ok(ApiResponse.success("检查完成", exists));
        } catch (Exception e) {
            log.error("检查用户名失败，用户名: {}", username, e);
            return ResponseEntity.ok(ApiResponse.error("检查用户名失败: " + e.getMessage()));
        }
    }

    @GetMapping("/check/email/{email}")
    @Operation(summary = "检查邮箱是否存在", description = "检查指定邮箱是否已被使用")
    public ResponseEntity<ApiResponse<Boolean>> checkEmailExists(
            @Parameter(description = "邮箱") @PathVariable String email) {
        try {
            boolean exists = userService.existsByEmail(email);
            return ResponseEntity.ok(ApiResponse.success("检查完成", exists));
        } catch (Exception e) {
            log.error("检查邮箱失败，邮箱: {}", email, e);
            return ResponseEntity.ok(ApiResponse.error("检查邮箱失败: " + e.getMessage()));
        }
    }
}

