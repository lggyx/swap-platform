package com.lggyx.service;

import com.lggyx.dto.LoginDTO;
import com.lggyx.dto.PasswordDTO;
import com.lggyx.dto.ProfileDTO;
import com.lggyx.dto.RegisterSellerDTO;
import com.lggyx.entity.Seller;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lggyx.result.Result;
import com.lggyx.vo.LoginVO;
import com.lggyx.vo.ProfileVO;
import com.lggyx.vo.RegisterVO;

/**
 * <p>
 * 卖家信息 服务类
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */
public interface ISellerService extends IService<Seller> {

    Result<RegisterVO> register(RegisterSellerDTO registerSellerDTO);

    Result<LoginVO> login(LoginDTO loginDTO);

    Result<ProfileVO> getProfile();

    Result<String> updateProfile(ProfileDTO profileDTO);

    Result<String> updatePassword(PasswordDTO passwordDTO);
}
