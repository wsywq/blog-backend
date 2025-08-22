package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章Mapper
 * 
 * @author blog
 * @since 2024-01-01
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    
    /**
     * 分页查询已发布的文章
     */
    IPage<Article> selectPublishedArticles(Page<Article> page);
    
    /**
     * 根据分类ID分页查询文章
     */
    IPage<Article> selectByCategoryId(Page<Article> page, @Param("categoryId") Long categoryId);
    
    /**
     * 根据标签ID分页查询文章
     */
    IPage<Article> selectByTagId(Page<Article> page, @Param("tagId") Long tagId);
    
    /**
     * 搜索文章
     */
    IPage<Article> searchArticles(Page<Article> page, @Param("keyword") String keyword);
    
    /**
     * 获取热门文章
     */
    List<Article> selectPopularArticles(@Param("limit") int limit);
    
    /**
     * 统计已发布文章数量
     */
    long countPublishedArticles();
    
    /**
     * 增加文章访问量
     */
    int incrementViewCount(@Param("id") Long id);
}
