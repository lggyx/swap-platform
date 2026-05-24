package com.lggyx.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentVO {
    @Schema(description = "主键")
    private Long id;

    @Schema(description = "旧物ID")
    private Long itemId;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "评论内容")
    private String content;

    @Schema(description = "回复内容")
    private String reply;

    @Schema(description = "创建时间")
    private LocalDateTime addTime;
}
