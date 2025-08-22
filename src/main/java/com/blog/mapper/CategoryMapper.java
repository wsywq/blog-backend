package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 分类Mapper
 * 
 * @author blog
 * @since 2024-01-01
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    
    /**
     * 根据名称查找分类
     */
    Category selectByName(@Param("name") String name);
    
    /**
     * 检查分类名称是否存在
     */
    boolean existsByName(@Param("name") String name);
}
