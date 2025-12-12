package com.lggyx.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ReplyDTO {
    @Schema(description = "回复内容")
    private String reply;
}
