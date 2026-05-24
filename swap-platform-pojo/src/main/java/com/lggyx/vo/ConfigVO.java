package com.lggyx.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ConfigVO {
    @Schema(description = "配置名称")
    private String name;
    @Schema(description = "配置值")
    private String value;
}
