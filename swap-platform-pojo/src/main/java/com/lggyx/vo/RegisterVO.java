package com.lggyx.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RegisterVO {
    @Schema(description = "用户id")
    private long user_id;
    @Schema(description = "用户名")
    private String username;
    @Schema(description = "token")
    private String token;
}
