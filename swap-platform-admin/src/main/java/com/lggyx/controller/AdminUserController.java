package com.lggyx.controller;


import com.lggyx.dto.AnnouncementsDTO;
import com.lggyx.dto.CommentDTO;
import com.lggyx.dto.ExchangeDealsDTO;
import com.lggyx.dto.ItemDTO;
import com.lggyx.entity.AdminUser;
import com.lggyx.result.PageResult;
import com.lggyx.result.Result;
import com.lggyx.service.IAdminUserService;
import com.lggyx.service.IAnnouncementsService;
import com.lggyx.service.IConfigService;
import com.lggyx.service.IExchangeDealsService;
import com.lggyx.service.IFavoritesService;
import com.lggyx.service.IItemCategoriesService;
import com.lggyx.service.IItemCommentsService;
import com.lggyx.service.IItemInfoService;
import com.lggyx.service.ISellerService;
import com.lggyx.service.IUserService;
import com.lggyx.vo.ConfigVO;
import com.lggyx.vo.ConfigVO;
import com.lggyx.vo.ExchangeDealsVO;
import com.lggyx.vo.StatisticsVO;
import com.lggyx.vo.StatisticsVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "后台管理")
public class AdminUserController {
    @Resource
    private IUserService userService;

    @Resource
    private ISellerService sellerService;

    @Resource
    private IItemInfoService itemInfoService;

    @Resource
    private IItemCommentsService itemCommentsService;

    @Resource
    private IExchangeDealsService exchangeDealsService;

    @Resource
    private IAdminUserService adminUserService;

    @Resource
    private IFavoritesService favoritesService;

    @Resource
    private IItemCategoriesService itemCategoriesService;

    @Resource
    private IAnnouncementsService announcementsService;

    @Resource
    private IConfigService configService;

    @GetMapping("/admin/users")
    @Operation(summary = "获取用户列表（管理员）")
    public Result<PageResult> getUserList(
            @RequestParam(required = false, defaultValue = "1") Long page,
            @RequestParam(required = false, defaultValue = "10") Long size
    ) {
        return userService.getUserList(page, size);
    }

    @PutMapping("/admin/items/{itemId}/audit")
    @Operation(summary = "管理员审核物品")
    public Result<String> auditItem(@PathVariable Long itemId, @RequestBody Map<String, String> body) {
        String approved = body.get("approved");
        String approvalReply = body.get("approvalReply");
        return itemInfoService.auditItem(itemId, approved, approvalReply);
    }

    @DeleteMapping("/admin/comments/{commentId}")
    @Operation(summary = "管理员删除评论")
    public Result<String> deleteComment(@PathVariable Long commentId) {
        return itemCommentsService.adminDeleteComment(commentId);
    }

    @GetMapping("/admin/statistics")
    @Operation(summary = "数据统计接口")
    public Result<StatisticsVO> getStatistics() {
        return adminUserService.getStatistics();
    }
}
