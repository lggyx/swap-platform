package com.lggyx.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lggyx.context.BaseContext;
import com.lggyx.dto.LoginDTO;
import com.lggyx.dto.PasswordDTO;
import com.lggyx.dto.ProfileDTO;
import com.lggyx.dto.RegisterSellerDTO;
import com.lggyx.entity.Seller;
import com.lggyx.enumeration.ErrorCode;
import com.lggyx.mapper.SellerMapper;
import com.lggyx.properties.JwtProperties;
import com.lggyx.result.Result;
import com.lggyx.service.ISellerService;
import com.lggyx.utils.JwtUtil;
import com.lggyx.vo.LoginVO;
import com.lggyx.vo.ProfileVO;
import com.lggyx.vo.RegisterVO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 卖家信息 服务实现类
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */
@Service
public class SellerServiceImpl extends ServiceImpl<SellerMapper, Seller> implements ISellerService {
    @Resource
    private SellerMapper sellerMapper;
    @Resource
    private JwtProperties jwtProperties;

    @Override
    public Result<RegisterVO> register(RegisterSellerDTO registerSellerDTO) {
        Seller seller = new Seller();
        BeanUtils.copyProperties(registerSellerDTO, seller);
        Seller selectOne = sellerMapper.selectOne(
                Wrappers.<Seller>lambdaQuery()
                        .eq(Seller::getSellerName, registerSellerDTO.getSellerName()));
        if (selectOne != null) {
            return Result.error(ErrorCode.USER_EXIST);
        }
        int insert = sellerMapper.insert(seller);
        if (insert > 0) {
            RegisterVO registerVO = new RegisterVO();
            BeanUtils.copyProperties(seller, registerVO);
            registerVO.setUserId(seller.getId());
            registerVO.setUsername(seller.getSellerName());
            return Result.success(registerVO);
        }
        return Result.error("注册失败");
    }

    @Override
    public Result<LoginVO> login(LoginDTO loginDTO) {
        Seller selectOne = sellerMapper.selectOne(
                Wrappers.<Seller>lambdaQuery()
                        .eq(Seller::getSellerName, loginDTO.getUsername())
                        .eq(Seller::getPassword, loginDTO.getPassword()));
        if (selectOne == null) {
            return Result.error(ErrorCode.USER_NOT_EXIST);
        }
        LoginVO loginVO = new LoginVO();
        BeanUtils.copyProperties(selectOne, loginVO);
        JwtUtil jwtUtil = new JwtUtil(jwtProperties);
        loginVO.setUserId(selectOne.getId());
        loginVO.setUsername(selectOne.getSellerName());
        loginVO.setRole("seller");
        loginVO.setToken(jwtUtil.generateToken("seller_" + selectOne.getSellerName()));
        loginVO.setExpireTime(String.valueOf(jwtProperties.getTtl()));
        return Result.success(loginVO);
    }

    @Override
    public Result<ProfileVO> getProfile() {
        String account = BaseContext.getCurrentAccount().substring(7);
        Seller seller = sellerMapper.selectOne(
                Wrappers.<Seller>lambdaQuery()
                        .eq(Seller::getSellerName, account));
        ProfileVO profileVO = new ProfileVO();
        BeanUtils.copyProperties(seller, profileVO);
        profileVO.setUsername(seller.getSellerName());
        return Result.success(profileVO);
    }

    @Override
    public Result<String> updateProfile(ProfileDTO profileDTO) {
        String account = BaseContext.getCurrentAccount().substring(7);
        Seller seller = sellerMapper.selectOne(
                Wrappers.<Seller>lambdaQuery()
                        .eq(Seller::getSellerName, account));
        seller.setRealName(profileDTO.getRealName());
        seller.setGender(profileDTO.getGender());
        seller.setPhone(profileDTO.getPhone());
        seller.setEmail(profileDTO.getEmail());
        int update = sellerMapper.updateById(seller);
        if (update > 0) {
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }

    @Override
    public Result<String> updatePassword(PasswordDTO passwordDTO) {
        String account = BaseContext.getCurrentAccount().substring(7);
        Seller seller = sellerMapper.selectOne(
                Wrappers.<Seller>lambdaQuery()
                        .eq(Seller::getSellerName, account));
        if (!seller.getPassword().equals(passwordDTO.getOldPassword())) {
            return Result.error(ErrorCode.PASSWORD_ERROR);
        }
        seller.setPassword(passwordDTO.getNewPassword());
        int update = sellerMapper.updateById(seller);
        if (update > 0) {
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }
}
