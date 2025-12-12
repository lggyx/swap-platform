package com.lggyx.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lggyx.context.BaseContext;
import com.lggyx.dto.LoginDTO;
import com.lggyx.dto.PasswordDTO;
import com.lggyx.dto.ProfileDTO;
import com.lggyx.dto.RegisterDTO;
import com.lggyx.entity.User;
import com.lggyx.enumeration.ErrorCode;
import com.lggyx.mapper.UserMapper;
import com.lggyx.properties.JwtProperties;
import com.lggyx.result.Result;
import com.lggyx.service.IUserService;
import com.lggyx.utils.JwtUtil;
import com.lggyx.vo.LoginVO;
import com.lggyx.vo.ProfileVO;
import com.lggyx.vo.RegisterVO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
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
    @Resource
    private UserMapper userMapper;
    @Resource
    private JwtProperties jwtProperties;

    @Override
    public User selectOne(User user) {
        return baseMapper.selectOne(
                Wrappers.<User>lambdaQuery()
                        .eq(User::getUsername, user.getUsername())
        );
    }

    @Override
    public Result<RegisterVO> register(RegisterDTO registerDTO) {
        User user = new User();
        BeanUtils.copyProperties(registerDTO, user);
        User selectOne = userMapper.selectOne(
                Wrappers.<User>lambdaQuery()
                        .eq(User::getUsername, registerDTO.getUsername()));
        if (selectOne != null) {
            return Result.error(ErrorCode.USER_EXIST);
        }
        int insert = userMapper.insert(user);
        if (insert > 0) {
            RegisterVO registerVO = new RegisterVO();
            BeanUtils.copyProperties(user, registerVO);
            return Result.success(registerVO);
        }
        return Result.error("注册失败");
    }

    @Override
    public Result<LoginVO> login(LoginDTO loginDTO) {
        User user = userMapper.selectOne(
                Wrappers.<User>lambdaQuery()
                        .eq(User::getUsername, loginDTO.getUsername()));
        if (user == null) {
            return Result.error(ErrorCode.USER_NOT_EXIST);
        }
        if (!user.getPassword().equals(loginDTO.getPassword())) {
            return Result.error(ErrorCode.PASSWORD_ERROR);
        }
        LoginVO loginVO = new LoginVO();
        BeanUtils.copyProperties(user, loginVO);
        JwtUtil jwtUtil = new JwtUtil(jwtProperties);
        loginVO.setUserId(user.getId());
        loginVO.setRole("user");
        loginVO.setToken(jwtUtil.generateToken("user___" + user.getUsername()));
        loginVO.setExpireTime(String.valueOf(jwtProperties.getTtl()));
        return Result.success(loginVO);
    }

    @Override
    public Result<ProfileVO> getProfile() {
        String account = BaseContext.getCurrentAccount().substring(7);
        User user = userMapper.selectOne(
                Wrappers.<User>lambdaQuery()
                        .eq(User::getUsername, account));
        ProfileVO profileVO = new ProfileVO();
        BeanUtils.copyProperties(user, profileVO);
        return Result.success(profileVO);
    }

    @Override
    public Result<String> updateProfile(ProfileDTO profileDTO) {
        return null;
    }

    @Override
    public Result<String> updatePassword(PasswordDTO passwordDTO) {
        String oldPassword = passwordDTO.getOldPassword();
        String newPassword = passwordDTO.getNewPassword();
        String account = BaseContext.getCurrentAccount().substring(7);
        User user = userMapper.selectOne(
                Wrappers.<User>lambdaQuery()
                        .eq(User::getUsername, account));
        if (!user.getPassword().equals(oldPassword)) {
            return Result.error(ErrorCode.PASSWORD_ERROR);
        }
        user.setPassword(newPassword);
        int update = userMapper.update(user,
                Wrappers.<User>lambdaQuery()
                        .eq(User::getUsername, account));
        if (update > 0) {
            return Result.success("修改成功");
        }
        return Result.error("修改失败");
    }
}
