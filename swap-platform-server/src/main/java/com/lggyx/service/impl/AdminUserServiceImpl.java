package com.lggyx.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lggyx.context.BaseContext;
import com.lggyx.dto.LoginDTO;
import com.lggyx.dto.PasswordDTO;
import com.lggyx.dto.ProfileDTO;
import com.lggyx.entity.AdminUser;
import com.lggyx.enumeration.ErrorCode;
import com.lggyx.mapper.AdminUserMapper;
import com.lggyx.properties.JwtProperties;
import com.lggyx.result.Result;
import com.lggyx.service.IAdminUserService;
import com.lggyx.utils.JwtUtil;
import com.lggyx.vo.LoginVO;
import com.lggyx.vo.ProfileVO;
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
        loginVO.setToken(jwtUtil.generateToken("admin__" + selectOne.getUsername()));
        loginVO.setExpireTime(String.valueOf(jwtProperties.getTtl()));
        return Result.success(loginVO);

    }

    @Override
    public Result<ProfileVO> getProfile() {
        String account = BaseContext.getCurrentAccount().substring(7);
        AdminUser adminUser = adminUserMapper.selectOne(
                Wrappers.<AdminUser>lambdaQuery()
                        .eq(AdminUser::getUsername, account));
        ProfileVO profileVO = new ProfileVO();
        BeanUtils.copyProperties(adminUser, profileVO);
        return Result.success(profileVO);
    }

    @Override
    public Result<String> updateProfile(ProfileDTO profileDTO) {
        return Result.success("管理员无需更新");
    }

    @Override
    public Result<String> updatePassword(PasswordDTO passwordDTO) {
        String account = BaseContext.getCurrentAccount().substring(7);
        AdminUser adminUser = adminUserMapper.selectOne(
                Wrappers.<AdminUser>lambdaQuery()
                        .eq(AdminUser::getUsername, account));
        adminUser.setPassword(passwordDTO.getNewPassword());
        return adminUserMapper.updateById(adminUser) > 0 ? Result.success() : Result.error(ErrorCode.PASSWORD_ERROR);
    }
}
