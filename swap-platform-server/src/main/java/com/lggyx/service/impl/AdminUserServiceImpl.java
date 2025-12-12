package com.lggyx.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lggyx.dto.LoginDTO;
import com.lggyx.entity.AdminUser;
import com.lggyx.enumeration.ErrorCode;
import com.lggyx.mapper.AdminUserMapper;
import com.lggyx.properties.JwtProperties;
import com.lggyx.result.Result;
import com.lggyx.service.IAdminUserService;
import com.lggyx.utils.JwtUtil;
import com.lggyx.vo.LoginVO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
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
    @Resource
    private AdminUserMapper adminUserMapper;
    @Resource
    private JwtProperties jwtProperties;

    @Override
    public Result<LoginVO> login(LoginDTO loginDTO) {
        AdminUser selectOne = adminUserMapper.selectOne(
                Wrappers.<AdminUser>lambdaQuery()
                        .eq(AdminUser::getUsername, loginDTO.getUsername()));
        if (selectOne == null) {
            return Result.error(ErrorCode.USER_NOT_EXIST);
        }
        if (!selectOne.getPassword().equals(loginDTO.getPassword())) {
            return Result.error(ErrorCode.PASSWORD_ERROR);
        }
        LoginVO loginVO = new LoginVO();
        BeanUtils.copyProperties(selectOne, loginVO);
        JwtUtil jwtUtil = new JwtUtil(jwtProperties);
        loginVO.setUserId(selectOne.getId());
        loginVO.setRole("admin");
        loginVO.setToken(jwtUtil.generateToken("admin_"+selectOne.getUsername()));
        loginVO.setExpireTime(String.valueOf(jwtProperties.getTtl()));
        return Result.success(loginVO);

    }
}
