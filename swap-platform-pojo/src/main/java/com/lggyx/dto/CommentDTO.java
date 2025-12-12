package com.lggyx.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CommentDTO {
    @Schema(description = "旧物ID")
    private long itemId;
    @Schema(description = "评论内容")
    private String content;
}
