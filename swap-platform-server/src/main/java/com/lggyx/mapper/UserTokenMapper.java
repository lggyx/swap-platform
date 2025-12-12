package com.lggyx.mapper;

import com.lggyx.entity.UserToken;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户登录令牌 Mapper 接口
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */
@Mapper
public interface UserTokenMapper extends BaseMapper<UserToken> {

}
