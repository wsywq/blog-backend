package com.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.dto.TagDto;
import com.blog.entity.Tag;
import com.blog.exception.ResourceNotFoundException;
import com.blog.mapper.TagMapper;
import com.blog.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 标签服务实现类
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TagServiceImpl implements TagService {

    private final TagMapper tagMapper;

    @Override
    @Transactional(readOnly = true)
    public List<TagDto> getAllTags() {
        List<Tag> tags = tagMapper.selectList(null);
        return tags.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public IPage<TagDto> getTags(Page<Tag> page) {
        IPage<Tag> tagPage = tagMapper.selectPage(page, null);
        return tagPage.convert(this::convertToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public TagDto getTagById(Long id) {
        Tag tag = tagMapper.selectById(id);
        if (tag == null) {
            throw new ResourceNotFoundException("标签不存在，ID: " + id);
        }
        return convertToDto(tag);
    }

    @Override
    @Transactional(readOnly = true)
    public TagDto getTagByName(String name) {
        Tag tag = tagMapper.selectByName(name);
        return tag != null ? convertToDto(tag) : null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDto> getTagsByNameIn(List<String> names) {
        List<Tag> tags = tagMapper.selectByNameIn(names);
        return tags.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TagDto createTag(TagDto tagDto) {
        // 检查标签名称是否已存在
        if (tagMapper.existsByName(tagDto.name())) {
            throw new IllegalArgumentException("标签名称已存在: " + tagDto.name());
        }

        Tag tag = convertToEntity(tagDto);
        tagMapper.insert(tag);
        log.info("创建标签成功: {}", tag.getName());
        return convertToDto(tag);
    }

    @Override
    public TagDto updateTag(Long id, TagDto tagDto) {
        Tag existingTag = tagMapper.selectById(id);
        if (existingTag == null) {
            throw new ResourceNotFoundException("标签不存在，ID: " + id);
        }

        // 检查标签名称是否被其他标签使用
        Tag tagByName = tagMapper.selectByName(tagDto.name());
        if (tagByName != null && !tagByName.getId().equals(id)) {
            throw new IllegalArgumentException("标签名称已存在: " + tagDto.name());
        }

        existingTag.setName(tagDto.name());
        existingTag.setColor(tagDto.color());

        tagMapper.updateById(existingTag);
        log.info("更新标签成功: {}", existingTag.getName());
        return convertToDto(existingTag);
    }

    @Override
    public void deleteTag(Long id) {
        Tag tag = tagMapper.selectById(id);
        if (tag == null) {
            throw new ResourceNotFoundException("标签不存在，ID: " + id);
        }

        tagMapper.deleteById(id);
        log.info("删除标签成功: {}", tag.getName());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return tagMapper.existsByName(name);
    }

    /**
     * 将实体转换为DTO
     */
    private TagDto convertToDto(Tag tag) {
        return new TagDto(
                tag.getId(),
                tag.getName(),
                tag.getColor(),
                tag.getCreateTime(),
                tag.getUpdateTime()
        );
    }

    /**
     * 将DTO转换为实体
     */
    private Tag convertToEntity(TagDto tagDto) {
        Tag tag = new Tag();
        tag.setName(tagDto.name());
        tag.setColor(tagDto.color());
        return tag;
    }
}
