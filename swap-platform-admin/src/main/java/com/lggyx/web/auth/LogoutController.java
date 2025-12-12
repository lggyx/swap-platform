package com.lggyx.web.auth;

import com.lggyx.context.BaseContext;
import com.lggyx.properties.JwtProperties;
import com.lggyx.result.Result;
import com.lggyx.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "用户认证管理")
@Slf4j
public class LogoutController {
    @Resource
    private JwtProperties jwtProperties;
    @PostMapping("/logout")
    @Operation(summary = "用户登出")
    public Result<String> logout() {
        JwtUtil jwtUtil = new JwtUtil(jwtProperties);
        jwtUtil.deleteToken(BaseContext.getCurrentToken());
        return Result.success(BaseContext.getCurrentAccount().substring(7)+"登出成功");
    }
}
