package com.blog.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 访客统计实体类
 * 
 * @author blog
 * @since 2024-01-01
 */
@Entity
@Table(name = "visitor_stats")
@Data
@EqualsAndHashCode(callSuper = true)
public class VisitorStats extends BaseEntity {
    
    @Column(length = 45)
    private String ipAddress;
    
    @Column(length = 500)
    private String userAgent;
    
    @Column(length = 500)
    private String referer;
    
    @Column(length = 500)
    private String pageUrl;
}
