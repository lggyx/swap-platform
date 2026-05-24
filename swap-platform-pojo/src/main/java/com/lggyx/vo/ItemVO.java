package com.lggyx.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ItemVO {
    @Schema(description = "旧物ID")
    private Long itemId;
}
