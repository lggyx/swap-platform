package com.lggyx.service.impl;

import com.lggyx.entity.User;
import com.lggyx.mapper.UserMapper;
import com.lggyx.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 平台用户 服务实现类
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
