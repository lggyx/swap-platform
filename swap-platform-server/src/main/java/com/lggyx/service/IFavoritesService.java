package com.lggyx.service;

import com.lggyx.dto.FavoriteDTO;
import com.lggyx.entity.Favorites;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lggyx.result.Result;

import java.util.List;

/**
 * <p>
 * 用户收藏 服务类
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */
public interface IFavoritesService extends IService<Favorites> {

    Result<String> addFavorite(FavoriteDTO favoriteDTO);

    Result<List<FavoriteDTO>> getFavoriteList();

    Result<String> cancelFavorite(Long id);
}
