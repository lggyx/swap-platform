package com.lggyx.web.user;


import com.lggyx.context.BaseContext;
import com.lggyx.dto.PasswordDTO;
import com.lggyx.dto.ProfileDTO;
import com.lggyx.enumeration.RoleCode;
import com.lggyx.result.Result;
import com.lggyx.service.IAdminUserService;
import com.lggyx.service.ISellerService;
import com.lggyx.service.IUserService;
import com.lggyx.vo.ProfileVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 平台用户 前端控制器
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */

@RestController
@RequestMapping("/api/user")
@Tag(name = "用户信息管理")
public class UserController {
    @Resource
    private IUserService userService;
    @Resource
    private ISellerService sellerService;
    @Resource
    private IAdminUserService adminUserService;

    @GetMapping("/profile")
    @Operation(summary = "获取用户信息")
    public Result<ProfileVO> getProfile() {
        // 获取当前账号标识
        String account = BaseContext.getCurrentAccount();
        if (account == null) {
            return Result.error("Invalid account");
        }
        // 根据账号前缀匹配角色（更安全）
        RoleCode roleCode = RoleCode.fromAccount(account);
        return switch (roleCode) {
            case USER_ROLE_CODE -> userService.getProfile();
            case SELLER_ROLE_CODE -> sellerService.getProfile();
            case ADMIN_ROLE_CODE -> adminUserService.getProfile();
            default -> Result.error("Invalid role");
        };
    }

    @PutMapping("/profile")
    @Operation(summary = "更新用户信息")
    public Result<String> updateProfile(@RequestBody ProfileDTO profileDTO) {
        // 获取当前账号标识
        String account = BaseContext.getCurrentAccount();
        if (account == null) {
            return Result.error("Invalid account");
        }
        // 根据账号前缀匹配角色（更安全）
        RoleCode roleCode = RoleCode.fromAccount(account);
        return switch (roleCode) {
            case USER_ROLE_CODE -> userService.updateProfile(profileDTO);
            case SELLER_ROLE_CODE -> sellerService.updateProfile(profileDTO);
            case ADMIN_ROLE_CODE -> adminUserService.updateProfile(profileDTO);
            default -> Result.error("Invalid role");
        };
    }

    @PutMapping("/password")
    @Operation(summary = "更新用户密码")
    public Result<String> updatePassword(@RequestBody PasswordDTO passwordDTO) {
        // 获取当前账号标识
        String account = BaseContext.getCurrentAccount();
        if (account == null) {
            return Result.error("Invalid account");
        }
        // 根据账号前缀匹配角色（更安全）
        RoleCode roleCode = RoleCode.fromAccount(account);
        return switch (roleCode) {
            case USER_ROLE_CODE -> userService.updatePassword(passwordDTO);
            case SELLER_ROLE_CODE -> sellerService.updatePassword(passwordDTO);
            case ADMIN_ROLE_CODE -> adminUserService.updatePassword(passwordDTO);
            default -> Result.error("Invalid role");
        };
    }

}
