package com.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分类实体类
 * 
 * @author blog
 * @since 2024-01-01
 */
@TableName("categories")
@Data
@EqualsAndHashCode(callSuper = true)
public class Category extends BaseEntity {
    
    @NotBlank(message = "分类名称不能为空")
    @TableField("name")
    private String name;
    
    @TableField("description")
    private String description;
    
    @TableField("icon")
    private String icon;
}
