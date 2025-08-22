package com.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 访客统计实体类
 * 
 * @author blog
 * @since 2024-01-01
 */
@TableName("visitor_stats")
@Data
@EqualsAndHashCode(callSuper = true)
public class VisitorStats extends BaseEntity {
    
    @TableField("ip_address")
    private String ipAddress;
    
    @TableField("user_agent")
    private String userAgent;
    
    @TableField("referer")
    private String referer;
    
    @TableField("page_url")
    private String pageUrl;
}
