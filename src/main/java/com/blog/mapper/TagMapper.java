package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 标签Mapper
 * 
 * @author blog
 * @since 2024-01-01
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    
    /**
     * 根据名称查找标签
     */
    Tag selectByName(@Param("name") String name);
    
    /**
     * 检查标签名称是否存在
     */
    boolean existsByName(@Param("name") String name);
    
    /**
     * 根据名称列表查找标签
     */
    List<Tag> selectByNameIn(@Param("names") List<String> names);
}
