package com.lggyx.mapper;

import com.lggyx.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 平台用户 Mapper 接口
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
