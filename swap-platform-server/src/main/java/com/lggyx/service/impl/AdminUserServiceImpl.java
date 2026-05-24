package com.lggyx.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lggyx.context.BaseContext;
import com.lggyx.dto.LoginDTO;
import com.lggyx.dto.PasswordDTO;
import com.lggyx.dto.ProfileDTO;
import com.lggyx.entity.AdminUser;
import com.lggyx.entity.User;
import com.lggyx.enumeration.ErrorCode;
import com.lggyx.enumeration.RoleCode;
import com.lggyx.mapper.AdminUserMapper;
import com.lggyx.mapper.ExchangeDealsMapper;
import com.lggyx.mapper.ItemInfoMapper;
import com.lggyx.mapper.SellerMapper;
import com.lggyx.mapper.UserMapper;
import com.lggyx.properties.JwtProperties;
import com.lggyx.result.PageResult;
import com.lggyx.result.Result;
import com.lggyx.service.IAdminUserService;
import com.lggyx.utils.JwtUtil;
import com.lggyx.vo.LoginVO;
import com.lggyx.vo.ProfileVO;
import com.lggyx.vo.StatisticsVO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements IAdminUserService {
    @Resource
    private AdminUserMapper adminUserMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private SellerMapper sellerMapper;
    @Resource
    private ItemInfoMapper itemInfoMapper;
    @Resource
    private ExchangeDealsMapper exchangeDealsMapper;
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
        if (!adminUser.getPassword().equals(passwordDTO.getOldPassword())) {
            return Result.error(ErrorCode.PASSWORD_ERROR);
        }
        adminUser.setPassword(passwordDTO.getNewPassword());
        return adminUserMapper.updateById(adminUser) > 0 ? Result.success("修改成功") : Result.error("修改失败");
    }

    @Override
    public Result<StatisticsVO> getStatistics() {
        RoleCode roleCode = RoleCode.fromAccount(BaseContext.getCurrentAccount());
        if (roleCode != RoleCode.ADMIN_ROLE_CODE) {
            return Result.error("权限不足");
        }
        StatisticsVO vo = new StatisticsVO();
        vo.setTotalUsers(userMapper.selectCount(null));
        vo.setTotalSellers(sellerMapper.selectCount(null));
        vo.setTotalItems(itemInfoMapper.selectCount(null));
        vo.setTotalDeals(exchangeDealsMapper.selectCount(null));
        return Result.success(vo);
    }
}
