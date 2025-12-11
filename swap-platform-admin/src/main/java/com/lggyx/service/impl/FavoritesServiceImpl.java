package com.lggyx.service.impl;

import com.lggyx.entity.Favorites;
import com.lggyx.mapper.FavoritesMapper;
import com.lggyx.service.IFavoritesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
