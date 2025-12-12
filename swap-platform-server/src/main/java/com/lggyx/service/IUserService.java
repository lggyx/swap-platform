package com.lggyx.service;

import com.lggyx.dto.LoginDTO;
import com.lggyx.dto.PasswordDTO;
import com.lggyx.dto.ProfileDTO;
import com.lggyx.dto.RegisterDTO;
import com.lggyx.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lggyx.result.Result;
import com.lggyx.vo.LoginVO;
import com.lggyx.vo.ProfileVO;
import com.lggyx.vo.RegisterVO;


/**
 * <p>
 * 平台用户 服务类
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */
public interface IUserService extends IService<User> {

    User selectOne(User user);

    Result<RegisterVO> register(RegisterDTO registerDTO);

    Result<LoginVO> login(LoginDTO loginDTO);

    Result<ProfileVO> getProfile();

    Result<String> updateProfile(ProfileDTO profileDTO);

    Result<String> updatePassword(PasswordDTO passwordDTO);
}
