package com.lggyx.service.impl;

import com.lggyx.entity.UserToken;
import com.lggyx.mapper.UserTokenMapper;
import com.lggyx.service.IUserTokenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户登录令牌 服务实现类
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */
@Service
public class UserTokenServiceImpl extends ServiceImpl<UserTokenMapper, UserToken> implements IUserTokenService {

}
