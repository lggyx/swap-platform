package com.lggyx.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class LoginVO {
    @Schema(description = "用户id")
    private long userId;
    @Schema(description = "用户名")
    private String username;
    @Schema(description = "用户角色")
    private String role;
    @Schema(description = "token")
    private String token;
    @Schema(description = "token过期时间")
    private String expireTime;
}
