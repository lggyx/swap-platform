package com.lggyx.controller;


import com.lggyx.entity.User;
import com.lggyx.result.Result;
import com.lggyx.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 平台用户 前端控制器
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */

@RestController
@RequestMapping("/user")
@Tag(name = "测试接口免token")
public class UserTestController {
    @Resource
    private IUserService userService;
    @PostMapping
    @Operation(summary = "根据用户名获取用户信息")
    public Result<User> getUser(@RequestBody User user){
        return Result.success(userService.selectOne(user));
    }
    @GetMapping
    @Operation(summary = "获取所有用户信息")
    public Result<List< User>> getAllUser(){
        return Result.success(userService.list());
    }
}
