package com.blog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

/**
 * 标签实体类
 * 
 * @author blog
 * @since 2024-01-01
 */
@Entity
@Table(name = "tags")
@Data
@EqualsAndHashCode(callSuper = true)
public class Tag extends BaseEntity {
    
    @NotBlank(message = "标签名称不能为空")
    @Column(nullable = false, unique = true, length = 50)
    private String name;
    
    @Column(length = 20)
    private String color;
    
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<Article> articles = new HashSet<>();
}
