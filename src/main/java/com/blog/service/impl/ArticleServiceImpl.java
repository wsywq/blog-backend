package com.blog.service.impl;

import com.blog.dto.ArticleDto;
import com.blog.dto.CreateArticleRequest;
import com.blog.entity.Article;
import com.blog.entity.Category;
import com.blog.entity.Tag;
import com.blog.exception.ResourceNotFoundException;
import com.blog.mapper.ArticleMapper;
import com.blog.repository.ArticleRepository;
import com.blog.repository.CategoryRepository;
import com.blog.repository.TagRepository;
import com.blog.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
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
    private ArticleRepository articleRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private TagRepository tagRepository;
    
    @Autowired
    private ArticleMapper articleMapper;
    
    @Override
    public Page<ArticleDto> getArticles(int page, int size, String sortBy, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        PageRequest pageable = PageRequest.of(page, size, sort);
        
        Page<Article> articles = articleRepository.findPublishedArticles(pageable);
        return articles.map(articleMapper::toDto);
    }
    
    @Override
    public ArticleDto getArticleById(Long id) {
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("文章不存在: " + id));
        return articleMapper.toDto(article);
    }
    
    @Override
    public ArticleDto createArticle(CreateArticleRequest request) {
        Article article = new Article();
        article.setTitle(request.title());
        article.setContent(request.content());
        article.setSummary(request.summary());
        article.setAuthor(request.author());
        article.setCoverImage(request.coverImage());
        
        if (request.categoryId() != null) {
            Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("分类不存在: " + request.categoryId()));
            article.setCategory(category);
        }
        
        if (request.tagIds() != null && !request.tagIds().isEmpty()) {
            Set<Tag> tags = tagRepository.findAllById(request.tagIds()).stream()
                .collect(Collectors.toSet());
            article.setTags(tags);
        }
        
        Article savedArticle = articleRepository.save(article);
        return articleMapper.toDto(savedArticle);
    }
    
    @Override
    public Page<ArticleDto> getArticlesByCategory(Long categoryId, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<Article> articles = articleRepository.findByCategoryId(categoryId, pageable);
        return articles.map(articleMapper::toDto);
    }
    
    @Override
    public Page<ArticleDto> getArticlesByTag(Long tagId, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<Article> articles = articleRepository.findByTagId(tagId, pageable);
        return articles.map(articleMapper::toDto);
    }
    
    @Override
    public Page<ArticleDto> searchArticles(String keyword, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<Article> articles = articleRepository.searchArticles(keyword, pageable);
        return articles.map(articleMapper::toDto);
    }
    
    @Override
    public List<ArticleDto> getPopularArticles(int limit) {
        PageRequest pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "viewCount"));
        List<Article> articles = articleRepository.findPopularArticles(pageable);
        return articles.stream()
            .map(articleMapper::toDto)
            .collect(Collectors.toList());
    }
    
    @Override
    public void incrementViewCount(Long id) {
        articleRepository.incrementViewCount(id);
    }
}
