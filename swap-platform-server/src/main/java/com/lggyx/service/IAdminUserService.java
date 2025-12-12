package com.lggyx.service;

import com.lggyx.dto.LoginDTO;
import com.lggyx.entity.AdminUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lggyx.result.Result;
import com.lggyx.vo.LoginVO;

/**
 * <p>
 * 后台管理员 服务类
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */
public interface IAdminUserService extends IService<AdminUser> {

    Result<LoginVO> login(LoginDTO loginDTO);
}
