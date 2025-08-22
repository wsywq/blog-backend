package com.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.blog.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户实体类
 */
@TableName("users")
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @NotBlank(message = "用户名不能为空")
    @TableField("username")
    private String username;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @TableField("email")
    private String email;

    @TableField("avatar")
    private String avatar;

    @TableField("github_id")
    private String githubId;

    @TableField("role")
    private UserRole role = UserRole.USER;
}
