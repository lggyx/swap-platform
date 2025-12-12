package com.lggyx.web.favorites;

import com.lggyx.dto.FavoriteDTO;
import com.lggyx.result.Result;
import com.lggyx.service.IFavoritesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "收藏管理")
public class FavoriteController {
    @Resource
    private IFavoritesService favoriteService;
    @PostMapping("/favorites")
    @Operation(summary = "添加收藏")
    public Result<String> addFavorite(@RequestBody FavoriteDTO favoriteDTO) {
        return favoriteService.addFavorite(favoriteDTO);
    }
    @GetMapping("/favorites")
    @Operation(summary = "获取收藏列表")
    public Result<List<FavoriteDTO>> getFavoriteList(){
        return favoriteService.getFavoriteList();
    }
    @DeleteMapping("/favorites/{id}")
    @Operation(summary = "取消收藏")
    public Result<String> cancelFavorite(@PathVariable Long id) {
        return favoriteService.cancelFavorite(id);
    }
}
