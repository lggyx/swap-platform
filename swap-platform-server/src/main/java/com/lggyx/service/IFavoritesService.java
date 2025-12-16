package com.lggyx.service;

import com.lggyx.result.PageResult;
import com.lggyx.vo.FavoriteVO;
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

    Result<String> addFavorite(FavoriteVO favoriteVO);

    Result<PageResult> getFavoriteList(Long page, Long size);

    Result<String> cancelFavorite(Long id);
}
