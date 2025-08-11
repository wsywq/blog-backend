package com.blog.enums;

/**
 * 文章状态枚举
 * 
 * @author blog
 * @since 2024-01-01
 */
public enum ArticleStatus {
    
    /**
     * 草稿
     */
    DRAFT("草稿"),
    
    /**
     * 已发布
     */
    PUBLISHED("已发布"),
    
    /**
     * 已归档
     */
    ARCHIVED("已归档");
    
    private final String description;
    
    ArticleStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
