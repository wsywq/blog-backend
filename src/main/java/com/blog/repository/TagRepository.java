package com.blog.repository;

import com.blog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 标签Repository
 * 
 * @author blog
 * @since 2024-01-01
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    
    /**
     * 根据名称查找标签
     */
    Tag findByName(String name);
    
    /**
     * 检查标签名称是否存在
     */
    boolean existsByName(String name);
    
    /**
     * 根据名称列表查找标签
     */
    List<Tag> findByNameIn(List<String> names);
}
