package com.blog.enums;

/**
 * 用户角色枚举
 * 
 * @author blog
 * @since 2024-01-01
 */
public enum UserRole {
    
    /**
     * 普通用户
     */
    USER("普通用户"),
    
    /**
     * 管理员
     */
    ADMIN("管理员");
    
    private final String description;
    
    UserRole(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
