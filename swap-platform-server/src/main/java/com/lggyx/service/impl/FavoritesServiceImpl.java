package com.lggyx.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lggyx.context.BaseContext;
import com.lggyx.entity.Favorites;
import com.lggyx.entity.User;
import com.lggyx.enumeration.ErrorCode;
import com.lggyx.enumeration.RoleCode;
import com.lggyx.enumeration.SuccessCode;
import com.lggyx.mapper.FavoritesMapper;
import com.lggyx.mapper.UserMapper;
import com.lggyx.result.PageResult;
import com.lggyx.result.Result;
import com.lggyx.service.IFavoritesService;
import com.lggyx.vo.FavoriteVO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户收藏 服务实现类
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */
@Service
public class FavoritesServiceImpl extends ServiceImpl<FavoritesMapper, Favorites> implements IFavoritesService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private FavoritesMapper favoritesMapper;

    @Override
    public Result<String> addFavorite(FavoriteVO favoriteVO) {
        RoleCode roleCode = RoleCode.fromAccount(BaseContext.getCurrentAccount());
        if (roleCode != RoleCode.USER_ROLE_CODE) {
            return Result.error(ErrorCode.NO_ACCESS_ADMIN_API);
        }
        Favorites favorites = new Favorites();
        BeanUtils.copyProperties(favoriteVO, favorites);
        Long userId = userMapper.selectOne(Wrappers.<User>lambdaQuery()
                        .eq(User::getUsername, BaseContext.getCurrentAccount()))
                .getId();
        favorites.setUserId(userId);
        return favoritesMapper.insert(favorites) > 0 ?
                Result.success(SuccessCode.SUCCESS) :
                Result.error(ErrorCode.SYSTEM_ERROR);
    }

    @Override
    public Result<PageResult> getFavoriteList(Long page, Long size) {
        RoleCode roleCode = RoleCode.fromAccount(BaseContext.getCurrentAccount());
        if (roleCode != RoleCode.USER_ROLE_CODE) {
            return Result.error(ErrorCode.NO_ACCESS_ADMIN_API);
        }
        Long userId = userMapper.selectOne(Wrappers.<User>lambdaQuery()
                        .eq(User::getUsername, BaseContext.getCurrentAccount().substring(7)))
                .getId();
        Page<Favorites> pageInfo = new Page<>(page, size);
        List<Favorites> favoritesList = favoritesMapper.selectPage(pageInfo,
                Wrappers.<Favorites>lambdaQuery()
                        .eq(Favorites::getUserId, userId)
        ).getRecords();
        List<FavoriteVO> favoriteVOS = favoritesList.stream().map(favorites -> {
            FavoriteVO favoriteVO = new FavoriteVO();
            BeanUtils.copyProperties(favorites, favoriteVO);
            return favoriteVO;
        }).toList();
        PageResult pageResult = new PageResult(
                favoriteVOS.size(),
                page,
                size,
                favoriteVOS
        );
        return Result.success(pageResult);
    }

    @Override
    public Result<String> cancelFavorite(Long id) {
        RoleCode roleCode = RoleCode.fromAccount(BaseContext.getCurrentAccount());
        if (roleCode != RoleCode.USER_ROLE_CODE) {
            return Result.error(ErrorCode.NO_ACCESS_ADMIN_API);
        }
        return favoritesMapper.delete(Wrappers.<Favorites>lambdaQuery()
                .eq(Favorites::getId, id)) > 0
                ? Result.success(SuccessCode.SUCCESS) : Result.error(ErrorCode.SYSTEM_ERROR);
    }
}
