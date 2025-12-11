package com.lggyx.service.impl;

import com.lggyx.entity.AdminUser;
import com.lggyx.mapper.AdminUserMapper;
import com.lggyx.service.IAdminUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台管理员 服务实现类
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */
@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements IAdminUserService {

}
