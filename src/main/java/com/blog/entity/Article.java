package com.blog.entity;

import com.blog.enums.ArticleStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 文章实体类
 * 
 * @author blog
 * @since 2024-01-01
 */
@Entity
@Table(name = "articles")
@Data
@EqualsAndHashCode(callSuper = true)
public class Article extends BaseEntity {
    
    @NotBlank(message = "文章标题不能为空")
    @Column(nullable = false, length = 200)
    private String title;
    
    @NotBlank(message = "文章内容不能为空")
    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    
    @NotBlank(message = "文章摘要不能为空")
    @Column(nullable = false, length = 500)
    private String summary;
    
    @NotBlank(message = "作者不能为空")
    @Column(nullable = false, length = 100)
    private String author;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ArticleStatus status = ArticleStatus.DRAFT;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "article_tags",
        joinColumns = @JoinColumn(name = "article_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();
    
    @Column(length = 500)
    private String coverImage;
    
    @Column(nullable = false)
    private Long viewCount = 0L;
    
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();
}
