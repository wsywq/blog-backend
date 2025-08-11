package com.blog.enums;

/**
 * 评论状态枚举
 * 
 * @author blog
 * @since 2024-01-01
 */
public enum CommentStatus {
    
    /**
     * 待审核
     */
    PENDING("待审核"),
    
    /**
     * 已通过
     */
    APPROVED("已通过"),
    
    /**
     * 已拒绝
     */
    REJECTED("已拒绝");
    
    private final String description;
    
    CommentStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
