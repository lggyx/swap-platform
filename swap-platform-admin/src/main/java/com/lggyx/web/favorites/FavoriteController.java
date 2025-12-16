package com.lggyx.web.favorites;

import com.lggyx.vo.FavoriteVO;
import com.lggyx.result.PageResult;
import com.lggyx.result.Result;
import com.lggyx.service.IFavoritesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "收藏管理")
public class FavoriteController {
    @Resource
    private IFavoritesService favoriteService;
    @PostMapping("/favorites")
    @Operation(summary = "添加收藏")
    public Result<String> addFavorite(@RequestBody FavoriteVO favoriteVO) {
        return favoriteService.addFavorite(favoriteVO);
    }
    @GetMapping("/favorites")
    @Operation(summary = "获取收藏列表")
    public Result<PageResult> getFavoriteList(
            @RequestParam(required = false, defaultValue = "1") Long page,
            @RequestParam(required = false, defaultValue = "10") Long size
    ){
        return favoriteService.getFavoriteList(page, size);
    }
    @DeleteMapping("/favorites/{id}")
    @Operation(summary = "取消收藏")
    public Result<String> cancelFavorite(@PathVariable Long id) {
        return favoriteService.cancelFavorite(id);
    }
}
