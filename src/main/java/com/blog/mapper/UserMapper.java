package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户数据访问层
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名查找用户
     */
    User selectByUsername(@Param("username") String username);

    /**
     * 根据邮箱查找用户
     */
    User selectByEmail(@Param("email") String email);

    /**
     * 根据GitHub ID查找用户
     */
    User selectByGithubId(@Param("githubId") String githubId);

    /**
     * 根据用户名或邮箱查找用户
     */
    User selectByUsernameOrEmail(@Param("usernameOrEmail") String usernameOrEmail);
}

