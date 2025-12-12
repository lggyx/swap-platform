package com.lggyx.controller;


import com.lggyx.entity.User;
import com.lggyx.result.Result;
import com.lggyx.service.IUserService;
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
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService userService;
    @PostMapping
    public Result<User> getUser(@RequestBody User user){
        return Result.success(userService.selectOne(user));
    }
}
