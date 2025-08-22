package com.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 标签实体类
 * 
 * @author blog
 * @since 2024-01-01
 */
@TableName("tags")
@Data
@EqualsAndHashCode(callSuper = true)
public class Tag extends BaseEntity {
    
    @NotBlank(message = "标签名称不能为空")
    @TableField("name")
    private String name;
    
    @TableField("color")
    private String color;
}
