package com.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.dto.ArticleDto;
import com.blog.dto.CreateArticleRequest;
import com.blog.entity.Article;

import java.util.List;

/**
 * 文章服务接口
 * 
 * @author blog
 * @since 2024-01-01
 */
public interface ArticleService {
    
    /**
     * 获取文章列表
     */
    IPage<ArticleDto> getArticles(int page, int size, String sortBy, String sortDir);
    
    /**
     * 根据ID获取文章详情
     */
    ArticleDto getArticleById(Long id);
    
    /**
     * 创建文章
     */
    ArticleDto createArticle(CreateArticleRequest request);
    
    /**
     * 根据分类获取文章
     */
    IPage<ArticleDto> getArticlesByCategory(Long categoryId, int page, int size);
    
    /**
     * 根据标签获取文章
     */
    IPage<ArticleDto> getArticlesByTag(Long tagId, int page, int size);
    
    /**
     * 搜索文章
     */
    IPage<ArticleDto> searchArticles(String keyword, int page, int size);
    
    /**
     * 获取热门文章
     */
    List<ArticleDto> getPopularArticles(int limit);
    
    /**
     * 增加文章访问量
     */
    void incrementViewCount(Long id);
}
