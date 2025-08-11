package com.blog.repository;

import com.blog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 分类Repository
 * 
 * @author blog
 * @since 2024-01-01
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    /**
     * 根据名称查找分类
     */
    Category findByName(String name);
    
    /**
     * 检查分类名称是否存在
     */
    boolean existsByName(String name);
}
