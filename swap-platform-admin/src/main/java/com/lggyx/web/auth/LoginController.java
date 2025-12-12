package com.lggyx.web.auth;

import com.lggyx.dto.LoginDTO;
import com.lggyx.result.Result;
import com.lggyx.service.IAdminUserService;
import com.lggyx.service.ISellerService;
import com.lggyx.service.IUserService;
import com.lggyx.vo.LoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "用户认证管理")
public class LoginController {
    @Resource
    private IUserService userService;
    @Resource
    private ISellerService sellerService;
    @Resource
    private IAdminUserService adminUserService;
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO loginDTO){
        String userType = loginDTO.getUserType();
        return switch (userType) {
            case "user" -> userService.login(loginDTO);
            case "seller" -> sellerService.login(loginDTO);
            case "admin" -> adminUserService.login(loginDTO);
            default -> Result.error("用户类型错误");
        };
    }

}
