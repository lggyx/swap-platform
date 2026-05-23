package com.lggyx.service;

import com.lggyx.dto.LoginDTO;
import com.lggyx.dto.PasswordDTO;
import com.lggyx.dto.ProfileDTO;
import com.lggyx.entity.AdminUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lggyx.result.PageResult;
import com.lggyx.result.Result;
import com.lggyx.vo.LoginVO;
import com.lggyx.vo.ProfileVO;
import com.lggyx.vo.StatisticsVO;

public interface IAdminUserService extends IService<AdminUser> {

    Result<LoginVO> login(LoginDTO loginDTO);

    Result<ProfileVO> getProfile();

    Result<String> updateProfile(ProfileDTO profileDTO);

    Result<String> updatePassword(PasswordDTO passwordDTO);

    Result<StatisticsVO> getStatistics();
}
