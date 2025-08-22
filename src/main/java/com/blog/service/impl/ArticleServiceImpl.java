package com.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.dto.ArticleDto;
import com.blog.dto.CreateArticleRequest;
import com.blog.entity.Article;
import com.blog.entity.Category;
import com.blog.entity.Tag;
import com.blog.enums.ArticleStatus;
import com.blog.exception.ResourceNotFoundException;
import com.blog.mapper.ArticleMapper;
import com.blog.mapper.CategoryMapper;
import com.blog.mapper.TagMapper;
import com.blog.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 文章服务实现类
 * 
 * @author blog
 * @since 2024-01-01
 */
@Service
@Transactional
@Slf4j
public class ArticleServiceImpl implements ArticleService {
    
    @Autowired
    private ArticleMapper articleMapper;
    
    @Autowired
    private CategoryMapper categoryMapper;
    
    @Autowired
    private TagMapper tagMapper;
    
    @Override
    public IPage<ArticleDto> getArticles(int page, int size, String sortBy, String sortDir) {
        Page<Article> pageParam = new Page<>(page, size);
        IPage<Article> articles = articleMapper.selectPublishedArticles(pageParam);
        return articles.convert(this::convertToDto);
    }
    
    @Override
    public ArticleDto getArticleById(Long id) {
        Article article = articleMapper.selectById(id);
        if (article == null) {
            throw new ResourceNotFoundException("文章不存在: " + id);
        }
        return convertToDto(article);
    }
    
    @Override
    public ArticleDto createArticle(CreateArticleRequest request) {
        Article article = new Article();
        article.setTitle(request.title());
        article.setContent(request.content());
        article.setSummary(request.summary());
        article.setAuthor(request.author());
        article.setCoverImage(request.coverImage());
        article.setStatus(ArticleStatus.DRAFT);
        
        if (request.categoryId() != null) {
            Category category = categoryMapper.selectById(request.categoryId());
            if (category == null) {
                throw new ResourceNotFoundException("分类不存在: " + request.categoryId());
            }
            article.setCategoryId(request.categoryId());
        }
        
        articleMapper.insert(article);
        
        // TODO: 处理标签关联，需要创建article_tags表的操作
        if (request.tagIds() != null && !request.tagIds().isEmpty()) {
            // 这里需要实现文章标签关联的逻辑
            log.info("文章标签关联功能待实现");
        }
        
        return convertToDto(article);
    }
    
    @Override
    public IPage<ArticleDto> getArticlesByCategory(Long categoryId, int page, int size) {
        Page<Article> pageParam = new Page<>(page, size);
        IPage<Article> articles = articleMapper.selectByCategoryId(pageParam, categoryId);
        return articles.convert(this::convertToDto);
    }
    
    @Override
    public IPage<ArticleDto> getArticlesByTag(Long tagId, int page, int size) {
        Page<Article> pageParam = new Page<>(page, size);
        IPage<Article> articles = articleMapper.selectByTagId(pageParam, tagId);
        return articles.convert(this::convertToDto);
    }
    
    @Override
    public IPage<ArticleDto> searchArticles(String keyword, int page, int size) {
        Page<Article> pageParam = new Page<>(page, size);
        IPage<Article> articles = articleMapper.searchArticles(pageParam, keyword);
        return articles.convert(this::convertToDto);
    }
    
    @Override
    public List<ArticleDto> getPopularArticles(int limit) {
        List<Article> articles = articleMapper.selectPopularArticles(limit);
        return articles.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    @Override
    public void incrementViewCount(Long id) {
        articleMapper.incrementViewCount(id);
    }
    
    /**
     * 将实体转换为DTO
     */
    private ArticleDto convertToDto(Article article) {
        return new ArticleDto(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getSummary(),
                article.getAuthor(),
                article.getStatus(),
                article.getCategoryId(),
                article.getCoverImage(),
                article.getViewCount(),
                article.getCreateTime(),
                article.getUpdateTime()
        );
    }
}
