package com.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.dto.TagDto;
import com.blog.entity.Tag;

import java.util.List;

/**
 * 标签服务接口
 */
public interface TagService {

    /**
     * 获取所有标签
     */
    List<TagDto> getAllTags();

    /**
     * 分页获取标签
     */
    IPage<TagDto> getTags(Page<Tag> page);

    /**
     * 根据ID获取标签
     */
    TagDto getTagById(Long id);

    /**
     * 根据名称获取标签
     */
    TagDto getTagByName(String name);

    /**
     * 根据名称列表获取标签
     */
    List<TagDto> getTagsByNameIn(List<String> names);

    /**
     * 创建标签
     */
    TagDto createTag(TagDto tagDto);

    /**
     * 更新标签
     */
    TagDto updateTag(Long id, TagDto tagDto);

    /**
     * 删除标签
     */
    void deleteTag(Long id);

    /**
     * 检查标签名称是否存在
     */
    boolean existsByName(String name);
}
