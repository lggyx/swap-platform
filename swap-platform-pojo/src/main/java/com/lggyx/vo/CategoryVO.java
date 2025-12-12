package com.lggyx.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryVO {
    @Schema(description = "主键")
    private Long id;

    @Schema(description = "创建时间")
    private LocalDateTime addTime;

    @Schema(description = "类型名称")
    private String categoryName;
}
