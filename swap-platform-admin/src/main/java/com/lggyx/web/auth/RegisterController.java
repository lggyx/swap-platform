package com.lggyx.web.auth;

import com.lggyx.dto.RegisterDTO;
import com.lggyx.result.Result;
import com.lggyx.service.ISellerService;
import com.lggyx.service.IUserService;
import com.lggyx.vo.RegisterVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/auth")
@Tag(name = "用户认证管理")
public class RegisterController {
    @Resource
    private IUserService userService;
    /**
     * 普通用户注册
     * @return 注册结果
     */
    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result<RegisterVO> register(@RequestBody RegisterDTO registerDTO) {
        return userService.register(registerDTO);
    }
    @Resource
    private ISellerService sellerService;
    /**
     * 卖家注册
     * @return 注册结果
     */
    @Operation(summary = "卖家注册")
    @PostMapping("/seller/register")
    public Result<RegisterVO> registerSeller(@RequestBody com.lggyx.dto.RegisterSellerDTO registerSellerDTO) {
        return sellerService.register(registerSellerDTO);
    }
}
